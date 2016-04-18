package org.javatigers.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@NamedStoredProcedureQuery(
		name = "users", 
		procedureName = "JAVATIGER.GETUSERS", 
		resultClasses = {Users.class }, 
		parameters = { 
					   @StoredProcedureParameter ( mode = ParameterMode.REF_CURSOR, type = void.class ),
					   @StoredProcedureParameter ( mode = ParameterMode.IN, type = String.class )
		}
)
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7709765606877065358L;

	@Id
	@GeneratedValue(generator = "USER_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 10, name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "DOB")
	private LocalDate dob;

	public Users() {
		// let ORM use this.
	}

	public Users(String firstName, String lastName, LocalDate dob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

}
