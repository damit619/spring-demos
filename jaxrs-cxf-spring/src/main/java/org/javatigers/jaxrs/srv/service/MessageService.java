package org.javatigers.jaxrs.srv.service;

import java.util.List;

import org.javatigers.jaxrs.srv.domain.Message;

public interface MessageService {
	
	List<Message> getMessages();

	Message getMessage(Long id);
	
	Message addMessage (Message message);
	
	Message updateMessage (Message message);
	
	Message deleteMessage (Long id);
}
