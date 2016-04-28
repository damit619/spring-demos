package org.javatigers.security.dao.users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javatigers.security.dao.base.BaseDAOImpl;
import org.javatigers.security.model.users.Users;
import org.springframework.stereotype.Repository;

/**
 * Perform CRUD opratiions on {@link Users}.
 * 
 * @author Amit Dhiman
 *
 */
@Repository
public class UsersDAOImpl extends BaseDAOImpl<Users> implements UsersDAO {
	
	/**
     * Persistence context to perform DB operations on entity.
     */
    @PersistenceContext
    private EntityManager entityManager;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Users readUser(String username) {
		return entityManager.createNamedQuery(READ_USER_BY_USERNAME, Users.class).setParameter(USERNAME, username).getSingleResult();
	}

}
