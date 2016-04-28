package org.javatigers.security.dao.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javatigers.security.model.EntityContract;
import org.springframework.stereotype.Repository;

/**
 * Generic DAO Implementation for CRUD operations on a JPA Entity.
 * 
 * @param T - is generic JPA entity.
 * 
 * @author Amit Dhiman
 *
 */
@Repository
public class BaseDAOImpl <T extends EntityContract> implements BaseDAO<T> {

	/**
     * Persistence context to perform DB operations on entity.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method to get the entityManager
     * 
     * @return {@link EntityManager}
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T createAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        entity = entityManager.merge(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T updateAndFlush(T entity) {
        entity = entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(Class<T> entityClass, Long id) {
        return (T) entityManager.find(entityClass, id);
    }
}
