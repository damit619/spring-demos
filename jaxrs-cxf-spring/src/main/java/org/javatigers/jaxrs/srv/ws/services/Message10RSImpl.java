package org.javatigers.jaxrs.srv.ws.services;

import java.util.List;

import javax.inject.Inject;

import org.javatigers.jaxrs.srv.domain.Message;
import org.javatigers.jaxrs.srv.service.MessageService;
import org.javatigers.jaxrs.srv.ws.JAXRSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JAXRSService(value = "message10RS")
public class Message10RSImpl implements Message10RS {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final MessageService messageService;
	
	@Inject
	public Message10RSImpl(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@Override
	public List<Message> listMessage() {
		logger.trace("listMessage {}.", messageService.getMessages());
		logger.debug("listMessage {}.", messageService.getMessages());
		logger.info("listMessage {}.", messageService.getMessages());
		return messageService.getMessages();
	}

	@Override
	public Message readMessage(Long messageId) {
		logger.trace("readMessage {}.", messageId);
		logger.debug("readMessage {}.", messageId);
		logger.info("readMessage {}.", messageId);
		return messageService.getMessage(messageId);
	}

}
