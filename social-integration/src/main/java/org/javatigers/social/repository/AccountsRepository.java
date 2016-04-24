package org.javatigers.social.repository;

import org.javatigers.social.domain.Accounts;
import org.springframework.data.repository.Repository;

/**
 * {@link Repository} to access {@link Accounts} instances.
 * {@link Repository<T, Serializable>} is marker interface that will let spring data know
 * about spring repositories T is type of domain and serializable is type of the id of domain object
 * 
 * @author amit
 */
public interface AccountsRepository {//extends JpaRepository<Accounts, Long> {
	
	/**
	 * Find {@link Accounts} by id.
	 * @param id
	 * @return {@link Accounts}
	 */
	Accounts findById(Long id);
	
	/**
	 * Find {@link Accounts} by email address of the user
	 * @param email
	 * @return {@link Accounts}
	 */
	Accounts findByEmailAddress (String email);
	
	/**
	 * Save supplied {@link Accounts} to the database.
	 * @param account
	 */
	void save (Accounts account);
	
	/**
	 * Update supplied {@link Accounts} to the database.
	 * @param account
	 * @return updated {@link Accounts}
	 */
	Accounts update (Accounts account);
	
	/**
	 * Find {@link Accounts} by username of the user
	 * @param email
	 * @return {@link Accounts}
	 */
	Accounts findByUsername (String username);

}
