package com.lz.springbatch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.lz.springbatch.domain.Message;

public class MessageItemReader implements ItemReader<Message>{
	
	@Override
	public Message read () throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
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
	    return reader.read();
	  }
}

