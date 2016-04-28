package org.javatigers.security.dao.users;

import org.javatigers.security.dao.base.BaseDAO;
import org.javatigers.security.model.users.Users;

/**
 * Perform CRUD opratiions on {@link Users}.
 * 
 * @author Amit Dhiman
 *
 */
public interface UsersDAO extends BaseDAO<Users>{
	
	/**
	 * JPQL named query to get {@link Users} by username.
	 */
	String READ_USER_BY_USERNAME = "read.user.byUsername";
	
	/**
	 * Username field of {@link Users}.
	 */
	String USERNAME = "username";
	
	/**
	 * Read and return {@link Users}
	 * 
	 * @param username
	 * @return - Users
	 */
	Users readUser(String username);
}
