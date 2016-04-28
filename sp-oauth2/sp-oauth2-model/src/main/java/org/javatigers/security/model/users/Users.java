package org.javatigers.security.model.users;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.javatigers.security.model.AbstractEntity;
import org.javatigers.security.model.EntityContract;
import org.javatigers.security.model.constants.AppModelConstants;

/**
 * Application users entity.
 * 
 * @author Amit Dhiman
 *
 */
@Entity
@Table(name = AppModelConstants.APP_USERS, uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class Users extends AbstractEntity implements EntityContract {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -8618499327567444784L;

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * Version used as optimistic locking
	 */
	@Version
	private Long version;

	/**
	 * Contains username for the User to login.
	 */
	@Column(name = "username", unique = true, length = 100, nullable = false)
	private String username;

	/**
	 * Contains password for the user to login.
	 */
	@Column(name = "password", length = 1024, nullable = false)
	private String password;

	/**
	 * Contains email address of the user which will be used for communication.
	 */
	@Column(length = 100, nullable = false)
	private String email;

	/**
	 * Contains first name of the user
	 */
	@Column(name = "first_name", length = 100, nullable = true)
	private String firstName;

	/**
	 * Contains last name of the user
	 */
	@Column(name = "last_name", length = 100, nullable = true)
	private String lastName;

	/**
	 * Represents weather user is active or not
	 */
	@Column(name = "is_active", columnDefinition = "tinyint(1) default '1'")
	private Boolean isActive = true;

	/**
	 * One Role can be assigned to multiple users
	 */
	@ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "app_users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") , inverseJoinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;

	/**
	 * Contains contact id of user.
	 */
	@Column(name = "contact_id", length = 50, nullable = false)
	private String contactId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	
}
