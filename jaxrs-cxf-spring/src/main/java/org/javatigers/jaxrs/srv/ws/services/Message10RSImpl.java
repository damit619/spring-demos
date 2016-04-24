package org.javatigers.jaxrs.srv.ws.services;

import java.util.List;

import javax.inject.Inject;

import org.javatigers.jaxrs.srv.domain.Message;
import org.javatigers.jaxrs.srv.service.MessageService;
import org.javatigers.jaxrs.srv.ws.JAXRSService;

@JAXRSService(value = "message10RS")
public class Message10RSImpl implements Message10RS {
	
	private final MessageService messageService;
	
	@Inject
	public Message10RSImpl(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@Override
	public List<Message> listMessage() {
		return messageService.getMessages();
	}

	@Override
	public Message readMessage(Long messageId) {
		return messageService.getMessage(messageId);
	}

}
