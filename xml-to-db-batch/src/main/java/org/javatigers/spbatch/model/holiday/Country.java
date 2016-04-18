package org.javatigers.spbatch.model.holiday;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1914964121937438936L;
	
	@Column(name = "country_code")
	private String code;
	
	@Column(name = "country_name")
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
