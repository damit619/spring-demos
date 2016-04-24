package org.javatigers.social.domain;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.Assert;

@Entity
@Table(name = "permission", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Permission implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8947711006396282623L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	/**
	 *For ORM
	 */
	public Permission () {}
	/**
	 * Create permission for the {@link Role}
	 * @param permission : must not be null
	 */
	public Permission (String name) {
		Assert.hasText(name, "Permission must not be null");
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}