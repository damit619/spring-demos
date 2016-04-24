package org.javatigers.jaxrs.srv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javatigers.jaxrs.srv.domain.Message;
import org.javatigers.jaxrs.srv.util.DBUtil;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{

	private Map<Long, Message> messageMap;

	public MessageServiceImpl() {
		messageMap = DBUtil.getMessageMap();
		messageMap.put(1l, new Message(1l, "message 1", "AD"));
		messageMap.put(2l, new Message(2l, "message 2", "AD"));
	}
	@Override
	public List<Message> getMessages() {
		return new ArrayList<>(messageMap.values());
	}
	@Override
	public Message getMessage(Long id) {
		return messageMap.get(id);
	}
	@Override
	public Message addMessage (Message message) {
		message.setId(new Long (messageMap.size()) + 1);
		messageMap.put(message.getId(), message);
		return messageMap.get(message.getId());
	}
	@Override
	public Message updateMessage (Message message) {
		if (messageMap.containsKey(message.getId())) {
			messageMap.put(message.getId(), message);
		}
		return messageMap.get(message.getId());
	}
	@Override
	public Message deleteMessage (Long id) {
		Message message = null;
		if (messageMap.containsKey(id)) {
			message = messageMap.remove(id);
		}
		return message;
	}
}
