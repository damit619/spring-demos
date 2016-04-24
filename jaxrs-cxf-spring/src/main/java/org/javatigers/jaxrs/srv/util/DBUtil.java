package org.javatigers.jaxrs.srv.util;

import java.util.HashMap;
import java.util.Map;

import org.javatigers.jaxrs.srv.domain.Message;

public class DBUtil {
	
	private static Map<Long, Message> messageMap = new HashMap<>();
	
	public static Map<Long, Message> getMessageMap () {
		return messageMap;
	}

}
