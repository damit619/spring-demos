package org.javatigers.social.service;

import org.javatigers.social.domain.Accounts;
import org.javatigers.social.exception.UsernameAlreadyExistException;

/**
 * Contract for services that work with an {@link Accounts}.
 *
 */

public interface AccountsService {
	
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
	 * @throws UsernameAlreadyExistException 
	 */
	void save (Accounts account) throws UsernameAlreadyExistException;
	
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
