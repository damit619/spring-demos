package org.javatigers.massanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javatigers.massanger.domain.Message;
import org.javatigers.massanger.util.DBUtil;

public class MessageService {

	private Map<Long, Message> messageMap;

	public MessageService() {
		messageMap = DBUtil.getMessageMap();
		messageMap.put(1l, new Message(1l, "message 1", "AD"));
		messageMap.put(2l, new Message(2l, "message 2", "AD"));
	}

	public List<Message> getMessages() {
		return new ArrayList<>(messageMap.values());
	}

	public Message getMessage(Long id) {
		return messageMap.get(id);
	}
	
	public Message addMessage (Message message) {
		message.setId(new Long (messageMap.size()) + 1);
		messageMap.put(message.getId(), message);
		return messageMap.get(message.getId());
	}
	
	public Message updateMessage (Message message) {
		if (messageMap.containsKey(message.getId())) {
			messageMap.put(message.getId(), message);
		}
		return messageMap.get(message.getId());
	}
	
	public Message deleteMessage (Long id) {
		Message message = null;
		if (messageMap.containsKey(id)) {
			message = messageMap.remove(id);
		}
		return message;
	}
}
