package org.javatigers.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.javatigers.domain.Users;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UsersDao{

	private static Long empCount = new Long(5);
	private static Map<Integer, Users> users;

	@Override
	public Users findById(Long id) {
		return users.get(id.intValue());
	}

	@Override
	public List<Users> findAll() {
		List<Users> usersList = new ArrayList<Users>();
		for(Map.Entry<Integer, Users> usersEntry : users.entrySet()) {
			usersList.add(usersEntry.getValue());
		}
		return usersList;
	}

	
	static {
		users = new TreeMap<Integer, Users>();
		Users user1 = new Users(new Long(1), "Amit", "Dhiman", "US");
		Users user2 = new Users(new Long(2), "Ashish", "Gupta", "India");
		Users user3 = new Users(new Long(3), "Rachit", "Bhasin", "India");
		Users user4 = new Users(new Long(4), "Sourav", "Sofat", "India");
		users.put(1, user1);
		users.put(2, user2);
		users.put(3, user3);
		users.put(4, user4);
	}


	@Override
	public Long delete(Long id) {
		empCount = users.remove(id.intValue()).getId();
		return empCount;
	}

	@Override
	public Users save(Users user) {
		if (user.getId() == null) {
			user.setId(empCount);
			users.put(empCount.intValue(), user);
			empCount++;
		} else if( ! users.containsKey(user.getId())) {
			user.setId(empCount);
			users.put(empCount.intValue(), user);
			empCount++;
		}
		return user;
	}

	@Override
	public Users update(Users user) {
		users.put(user.getId().intValue(), user);
		return user;
	}
}
