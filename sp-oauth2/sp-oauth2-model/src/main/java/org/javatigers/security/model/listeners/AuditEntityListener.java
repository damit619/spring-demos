package org.javatigers.security.model.listeners;

import java.time.LocalDate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.javatigers.security.model.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Audit entity listener class for setting the audit column values on persist or
 * update operations. Internally invoked by JPA.
 * 
 * @author Amit Dhiman
 *
 */
@Configurable(value = "auditEntityListener", dependencyCheck = true)
public class AuditEntityListener {
	
	/**
	 * Logging events.
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
     * This method is called before persist. Used to set the ADD_DATE values for new entity.
     * 
     * @param entity
     */
	@PrePersist
	public void prePersist (Object entity) {
		logger.debug("In PrePersist of {}", entity.getClass());
        if (entity instanceof AbstractEntity) {
            AbstractEntity abstractEntity = (AbstractEntity) entity;
            abstractEntity.setAddDate(LocalDate.now());
        }
	}
	
	/**
     * This method is called before update. Used to set the MOD_DATE values for existing entity.
     * 
     * @param entity
     */
	@PreUpdate
	public void preUpdate (Object entity) {
		logger.debug("In PreUpdate of {} ", entity.getClass());
        if (entity instanceof AbstractEntity) {
            AbstractEntity abstractEntity = (AbstractEntity) entity;
            abstractEntity.setModifiedDate(LocalDate.now());
        }
	}
}
