package org.javatigers.jpa.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import junit.framework.TestCase;

public class AbstractTest extends TestCase {
	
	private static final String PERSISTENCE_UNIT_NAME = "org.hibernate.jpa";
	protected EntityManagerFactory emf;
	
	@Override
	protected void setUp() {
		// like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
		// 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	@Test
	public void test () {
		
	}
	
	protected EntityManager createEntityManager () {
		return emf.createEntityManager();
	}
	
	@Override
	protected void tearDown () {
		emf.close();
	}
}
