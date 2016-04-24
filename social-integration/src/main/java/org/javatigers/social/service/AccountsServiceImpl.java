package org.javatigers.social.service;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.javatigers.social.domain.Accounts;
import org.javatigers.social.domain.Permission;
import org.javatigers.social.domain.Role;
import org.javatigers.social.exception.UsernameAlreadyExistException;
import org.javatigers.social.repository.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @see AccountsService
 *
 */
@Service("accountsService")
@Transactional(readOnly = true)
public class AccountsServiceImpl implements AccountsService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);
	
	private final AccountsRepository accountsRepository; 
	
	@Inject
	public AccountsServiceImpl (AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}
	
	@Override
	public Accounts findById(Long id) {
		return this.accountsRepository.findById(id);
	}
	
	/**
	 *We need {@link Role} and {@link Permission} to populate {@link GrantedAuthority}
	 *of spring security to work with role based authorization for this we need to 
	 *initialize {@link Role} and {@link Permission}. 
	 */
	@Override
	public Accounts findByEmailAddress(String email) {
		
		Accounts accounts = this.accountsRepository.findByEmailAddress(email);
		Hibernate.initialize(accounts.getRoles());
		for (Role role : accounts.getRoles()) {
			Hibernate.initialize(role.getPermissions());
		}
		logger.debug("Roles and Permissions have initialized");
		return accounts;
	}
	
	@Transactional(readOnly = false)
	@Override
	public void save(Accounts account) throws UsernameAlreadyExistException {
		try {
			account.setPassword(DigestUtils.sha256Hex(account.getPassword()));
			
			account.getRoles().add(new Role("ROLE_USER"));
			account.setEnable(true);
			this.accountsRepository.save(account);
		} catch (EntityExistsException e) {
			throw new UsernameAlreadyExistException(account.getUsername());
		}
		

	}

	@Transactional(readOnly = false)
	@Override
	public Accounts update(Accounts account) {
		return this.accountsRepository.update(account);
	}

	@Override
	public Accounts findByUsername(String username) {
		Accounts accounts = this.accountsRepository.findByUsername(username);
		Hibernate.initialize(accounts.getRoles());
		for (Role role : accounts.getRoles()) {
			Hibernate.initialize(role.getPermissions());
		}
		logger.debug("Roles and Permissions have initialized");
		return accounts;
	}

}
