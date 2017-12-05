package com.lz.springbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component  
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {  
  
    @Override  
    public void afterJob(JobExecution jobExecution) {  
          
    }  
  
    @Override  
    public void beforeJob(JobExecution jobExecution) {  
        super.beforeJob(jobExecution);  
    }  
}  