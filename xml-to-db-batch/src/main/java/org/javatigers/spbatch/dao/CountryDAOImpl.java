package org.javatigers.spbatch.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javatigers.spbatch.model.holiday.Country;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CountryDAOImpl extends GenericDAOImpl<Country, Long> implements CountryDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public CountryDAOImpl () {
			
		super(Country.class);
		
	}
	
	@Transactional
	@Override
	public Country getSystemCountry(String countryCode) {
		String sql = "SELECT c FROM Country c WHERE c.code = :code";
		
		return em.createQuery(sql, Country.class).setParameter("code", countryCode).getSingleResult();
	}
	
}
