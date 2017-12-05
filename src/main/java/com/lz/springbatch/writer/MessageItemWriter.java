package com.lz.springbatch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.lz.springbatch.domain.Message;

public class MessageItemWriter implements ItemWriter<Message>{
	public void write(List<? extends Message> messages) throws Exception {
		System.out.println("write results");
		for (Message m : messages) {
			System.out.println(m.getContent());
		}
	}
}
