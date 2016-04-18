package org.javatigers.spbatch.dao;

import java.io.Serializable;
/**
 * Generic interface for database genric oprations like CRUD.
 * @author Amit Dhiman
 *
 */
public interface GenericDAO<T, ID extends Serializable> {
	
	 /**
	   * Persist the newInstance object into database
	   * 
	   * @throws Exception
	   */
	T create (T t) throws Exception;
	
	/**
	 * Merge already persisted instance.
	 * @param t
	 * @return t
	 * @throws Exception
	 */
	T update (T t) throws Exception;
}
