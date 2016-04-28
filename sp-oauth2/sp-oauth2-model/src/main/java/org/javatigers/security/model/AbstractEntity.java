package org.javatigers.security.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.javatigers.security.model.listeners.AuditEntityListener;

/**
 * Abstract entity for storing initial details.
 * 
 * @author Amit Dhiman
 *
 */
@EntityListeners(AuditEntityListener.class)
@MappedSuperclass
public class AbstractEntity extends AbstractVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027377900382366050L;
	
	@Column(name = "ADD_DATE", updatable = false)
	private LocalDate addDate;
	
	@Column(name = "MOD_DATE")
	private LocalDate modifiedDate;

	/**
	 * @return the addDate
	 */
	public LocalDate getAddDate() {
		return addDate;
	}

	/**
	 * @param addDate the addDate to set
	 */
	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
