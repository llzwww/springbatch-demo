package com.lz.springbatch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.lz.springbatch.domain.Message;
import com.lz.springbatch.listener.RetryFailuireItemListener;
import com.lz.springbatch.processor.MessageItemProcessor;
import com.lz.springbatch.writer.MessageItemWriter;

@Configuration  
@EnableBatchProcessing  
public class MessageBatchConfiguration {  
      
      
    private static final Logger logger = LoggerFactory.getLogger(MessageBatchConfiguration.class);  
  
    /**
     * 读数据
     * @return
     */
    @Bean 
    public ItemReader<Message> reader() {
    	logger.info("reader()");
        FlatFileItemReader<Message> reader = new FlatFileItemReader<Message>();  
        reader.setResource(new ClassPathResource("msg.txt"));  
        reader.setLineMapper(new DefaultLineMapper<Message>() {{  
            setLineTokenizer(new DelimitedLineTokenizer() {{  
                setNames(new String[] {"title","content"});  
            }});  
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Message>() {{  
                setTargetType(Message.class);  
            }});  
        }});  
        return reader;  
    }  
  
    /** 
     * 处理数据 
     * @return 
     */  
    @Bean  
    public MessageItemProcessor processor() {  
    	logger.info("processor()");
        return new MessageItemProcessor();  
    }  
  
    /** 
     * 输出数据 
     * @return 
     */  
    @Bean  
    public MessageItemWriter writer() {  
    	logger.info("writer()");
        return new MessageItemWriter();  
    }  
  
    @Bean  
    public Job readerMsgJob(JobBuilderFactory jobs, Step step,JobExecutionListener listener,JobRepository jobRepository) {
    	logger.info("readerMsgJob()");
        return jobs.get("readerMsgJob")  
                .incrementer(new RunIdIncrementer())  
                .repository(jobRepository)  
                .listener(listener)  
                .flow(step)  
                .end()  
                .build();  
    }  
      
    @Bean  
    public Step msgStep(StepBuilderFactory stepBuilderFactory, 
    		ItemReader<Message> reader,  
            ItemWriter<Message> writer, 
            ItemProcessor<Message, Message> processor,
            PlatformTransactionManager transactionManager) {  
    	logger.info("msgStep()");
        return stepBuilderFactory.get("msgStep")  
                .<Message, Message> chunk(500)  
                .reader(reader)  
                .processor(processor)  
                .writer(writer)  
                .faultTolerant()  
                .retry(Exception.class)   // 重试  
                .noRetry(ParseException.class)  
                .retryLimit(1)           //每条记录重试一次  
                .listener(new RetryFailuireItemListener())    
                .skip(Exception.class)  
                .skipLimit(500)         //一共允许跳过200次异常  
                .taskExecutor(new SimpleAsyncTaskExecutor()) //设置并发方式执行  
                .throttleLimit(10)        //并发任务数为 10,默认为4  
                .transactionManager(transactionManager)  
                .build();  
    }  
      
}  