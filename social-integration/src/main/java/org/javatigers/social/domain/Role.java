package org.javatigers.social.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.Assert;
/**
 * Roles are the rights for the {@link Accounts} that needs to be verified when user performs
 * a secure action.
 * @author amit.d
 */
@Entity
@Table(name = "role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Role implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1748021680833386255L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	/**
	 * {@link Permission are associated with roles}
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	private List <Permission> permissions = new ArrayList <Permission>();

	/**
	 * For ORM
	 */
	public Role () {}
	/**
	 * Repersents roles for the {@link Accounts} 
	 * @param role: must not be null
	 */
	public Role (String role) {
		Assert.hasText(role, "role must not be null");
		this.name = role;
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
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
