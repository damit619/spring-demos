package org.javatigers.security.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javatigers.security.model.AbstractEntity;
import org.javatigers.security.model.constants.AppModelConstants;
import org.springframework.util.Assert;
/**
 * Roles are the rights for the {@link Users} that needs to be verified when user performs
 * a secure action.
 * 
 * @author amit.d
 */
@Entity
@Table(name = AppModelConstants.ROLES, uniqueConstraints = {@UniqueConstraint(columnNames = "role_name")})
public class Role extends AbstractEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "role_name")
	private String roleName;

	/**
	 * For ORM
	 */
	public Role () {}
	/**
	 * Repersents roles for the {@link Users} 
	 * @param role: must not be null
	 */
	public Role (String roleName) {
		Assert.hasText(roleName, "role must not be null");
		this.roleName = roleName;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String toString () {
		
		ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("ID : ", this.getId())
		.append("Role Name : ", this.getRoleName());
		
		return toStringBuilder.build();
	}
	
}
