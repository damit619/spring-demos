package org.javatigers.jaxrs.srv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javatigers.jaxrs.srv.domain.Message;
import org.javatigers.jaxrs.srv.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Map<Long, Message> messageMap;

	public MessageServiceImpl() {
		messageMap = DBUtil.getMessageMap();
		messageMap.put(1l, new Message(1l, "message 1", "AD"));
		messageMap.put(2l, new Message(2l, "message 2", "AD"));
	}
	@Override
	public List<Message> getMessages() {
		logger.trace("getMessages {}.", messageMap.values());
		logger.debug("getMessages {}.", messageMap.values());
		return new ArrayList<>(messageMap.values());
	}
	@Override
	public Message getMessage(Long id) {
		logger.trace("getMessage {}.", id);
		logger.debug("getMessage {}.", id);
		return messageMap.get(id);
	}
	@Override
	public Message addMessage (Message message) {
		logger.trace("addMessage {}.", message);
		logger.debug("addMessage {}.", message);
		message.setId(new Long (messageMap.size()) + 1);
		messageMap.put(message.getId(), message);
		return messageMap.get(message.getId());
	}
	@Override
	public Message updateMessage (Message message) {
		logger.trace("updateMessage {}.", message);
		if (messageMap.containsKey(message.getId())) {
			messageMap.put(message.getId(), message);
		}
		return messageMap.get(message.getId());
	}
	@Override
	public Message deleteMessage (Long id) {
		logger.trace("updateMessage {}.", id);
		Message message = null;
		if (messageMap.containsKey(id)) {
			message = messageMap.remove(id);
		}
		return message;
	}
}
