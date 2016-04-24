package org.javatigers.service;

import java.util.List;

import org.javatigers.dao.UsersDao;
import org.javatigers.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UsersDao usersDao;
	
	@Override
	public Users findById(Long id) {
		return this.usersDao.findById(id);
	}

	@Override
	public List<Users> findAll() {
		return this.usersDao.findAll();
	}

	@Override
	public Long delete(Long id) {
		return this.usersDao.delete(id);
	}

	@Override
	public Users save(Users user) {
		return this.usersDao.save(user);
	}

	@Override
	public Users update(Users user) {
		return this.usersDao.update(user);
	}
}
