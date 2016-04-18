package org.javatigers.spbatch.model.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -515499125001992447L;
	
	/**
	 * Returns the identifier for the entity
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "created_on", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createOn;
	
	@Column(name = "modified_on")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime modifiedOn;
	
	@Version
	private Long version;

	public DateTime getCreateOn() {
		return createOn;
	}

	public void setCreateOn(DateTime createOn) {
		this.createOn = createOn;
	}

	public DateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(DateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	public void prePersist() {
		DateTime now = DateTime.now();
		this.setCreateOn(now);
		//this.setModifiedOn(now);
	}
	
	public void preUpdate () {
		DateTime now = DateTime.now();
		this.setModifiedOn(now);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(this.id == null || obj == null || ! (this.getClass().equals(obj.getClass()))) {
			return false;
		}
		AbstractEntity abstractEntity = (AbstractEntity) obj;
		return this.getId().equals(abstractEntity.getId());
	}
	
	@Override
	public int hashCode() {
		return this.getId() == null ? 0 : this.getId().hashCode();
	}


}
