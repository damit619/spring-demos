package org.javatigers.security.dao.base;

import org.javatigers.security.model.EntityContract;

/**
 * Perform generic CRUD operations on JPA entities.
 * 
 * @param T - is generic JPA entity.
 * 
 * @author Amit Dhiman
 *
 */
public interface BaseDAO <T extends EntityContract> {
	
	/**
     * Method to create an entity
     * 
     * @param entity
     * @return T - the entity to save
     */
    public T create(T entity);

    /**
     * Method to create and flush(Synch with DB) an entity
     * 
     * @param entity
     * @return T - the entity to save
     */
    public T createAndFlush(T entity);

    /**
     * Method to update an entity
     * 
     * @param entity
     * @return T - the entity to update
     */
    public T update(T entity);

    /**
     * Method to update and flush(Synch with DB) an entity
     * 
     * @param entity
     * @return T - the entity to update
     */
    public T updateAndFlush(T entity);

    /**
     * Method to delete an entity
     * 
     * @param entity
     */
    public void delete(T entity);

    /**
     * Method to get an entity from PK identifier
     * 
     * @param entityClass
     * @param id
     *            - primary identifier
     * @return T - fetched entity
     */
    public T get(Class<T> entityClass, Long id);

}
