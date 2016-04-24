package org.javatigers.service;

import java.util.List;

import org.javatigers.domain.Users;

public interface UserService {

	Users findById(Long id);
	
	List<Users> findAll();
	
	Long delete(Long id);
	
	Users save(Users user);
	
	Users update(Users user);
}
