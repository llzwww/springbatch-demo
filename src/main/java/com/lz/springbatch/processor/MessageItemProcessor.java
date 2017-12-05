package com.lz.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.lz.springbatch.domain.Message;

public class MessageItemProcessor implements ItemProcessor<Message, Message> {

	public String inputFile;

	public MessageItemProcessor() {
	}

	public MessageItemProcessor(String inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public Message process(Message msg) throws Exception {
		Message m = new Message();
		m.setTitle(msg.getTitle());
		m.setContent("Hello " + msg.getTitle() + ", " + msg.getContent() + " .");
		return m;
	}

}
