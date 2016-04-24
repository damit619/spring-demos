package org.javatigers.social.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.util.Assert;

/**
 * An Address represents the address of an {@link Accounts}. There is no mean to
 * create a saparate entity because an address never exist without an
 * {@link Accounts}.
 * 
 * @author amit.d
 */
@Embeddable
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -416240430260372046L;
	private String street, city, country;

	/**
	 * Creates a new {@link Address} from the given street, city and country.
	 * 
	 * @param street must not be {@literal null} or empty.
	 * @param city must not be {@literal null} or empty.
	 * @param country must not be {@literal null} or empty.
	 */
	public Address(String street, String city, String country) {

		Assert.hasText(street, "Street must not be null or empty!");
		Assert.hasText(city, "City must not be null or empty!");
		Assert.hasText(country, "Country must not be null or empty!");

		this.street = street;
		this.city = city;
		this.country = country;
	}

	public Address() {

	}

	/**
	 * Returns a copy of the current {@link Address} instance which is a new entity in terms of persistence.
	 * 
	 * @return
	 */
	public Address getCopy() {
		return new Address(this.street, this.city, this.country);
	}

	/**
	 * Returns the street.
	 * 
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Returns the city.
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the country.
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
