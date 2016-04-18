package org.javatigers.spbatch.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Transactional
public class GenericDAOImpl <T, ID extends Serializable> implements GenericDAO<T, ID> {
	
	private static final Logger logger = LoggerFactory.getLogger(GenericDAOImpl.class);

	private Class<? extends T> clazz;
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 *  Default constructor
	 */
	public GenericDAOImpl () {}
	
	public GenericDAOImpl (Class<? extends T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public T create(T t) throws Exception {
		logger.debug("creating {}", t.getClass().getName());
		em.persist(t);
		return t;
	}
	
	@Transactional
	@Override
	public T update(T t) throws Exception {
		logger.debug("merging  {}", t.getClass().getName());
		return em.merge(t);
	}
	
}
