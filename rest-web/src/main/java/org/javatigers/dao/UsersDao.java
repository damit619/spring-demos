package org.javatigers.dao;

import java.util.List;

import org.javatigers.domain.Users;

public interface UsersDao {
	
	Users findById(Long id);
	
	List<Users> findAll();
	
	Long delete(Long id);
	
	Users save (Users user);
	
	Users update(Users user);
}
