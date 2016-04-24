package org.javatigers.social.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.javatigers.social.enums.SocialMediaService;
import org.springframework.util.Assert;

/**
 * A account resembles an authenticated user of our system. A account is able to
 * submit orders. A account is identified by his or her username. When
 * authenticating the user supplies its username and password. Besides
 * identification information we also store basic legal information such as
 * address, firstname, lastname and email address.
 * 
 * @author amit.kumar1
 */
@Entity
@Table(name = "account", uniqueConstraints = {@UniqueConstraint(columnNames = { "username" })})
public class Accounts extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6354135979485825414L;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	private String username;

	private String password;
	
	private boolean enabled;
	
	@Column(name = "email_address", unique = true)
	private String emailAddress;

	/**
	 * represents an {@link Address} of an Account
	 */
	@Embedded
	private Address address;
	/**
	 * {@link Role} for the account
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Role> roles = new ArrayList<Role>();
	
	/**
	 * {@link SocialMediaService} for the account
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "sign_in_provider")
    private SocialMediaService signInProvider;
	
	public Accounts() {
	}

	/**
	 * Create Account with only initial values
	 * 
	 * @param username
	 *            must not be null
	 * @param password
	 *            must not be null
	 * @param emailAddress
	 *            must not be null
	 */
	public Accounts(String username, String password, String emailAddress) {
		Assert.hasText(username, "Username must not be null");
		Assert.hasText(password, "Password must not be null");
		Assert.hasText(emailAddress, "Email address must not be null");

		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {

		return "ID : " + this.getId() + " Username : " + this.getUsername();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnable(boolean enabled) {
		this.enabled = enabled;
	}

	public SocialMediaService getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(SocialMediaService signInProvider) {
		this.signInProvider = signInProvider;
	}
	
}
