package org.javatigers.social.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javatigers.social.domain.Accounts;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryImpl implements AccountsRepository {
	
	private static final String FIND_BY_EMAILID = "SELECT account FROM Accounts account WHERE account.emailAddress =:email";
	
	private static final String FIND_BY_USERNAME = "SELECT account FROM Accounts account WHERE account.username =:user";
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Accounts findByEmailAddress(String email) {
		
		return em.createQuery(FIND_BY_EMAILID, Accounts.class).setParameter("email", email).getSingleResult();
	}

	@Override
	public void save(Accounts account) {
		em.persist(account);
	}

	@Override
	public Accounts findById(Long id) {
		
		return em.find(Accounts.class, id);
	}

	@Override
	public Accounts update(Accounts account) {
		
		return em.merge(account);
	}

	@Override
	public Accounts findByUsername(String username) {
		return em.createQuery(FIND_BY_USERNAME, Accounts.class).setParameter("user", username).getSingleResult();
	}
	
}
