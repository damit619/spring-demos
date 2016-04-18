package org.javatigers.spbatch.dao;

import org.javatigers.spbatch.model.holiday.Country;

public interface CountryDAO extends GenericDAO<Country, Long> {
	
	Country getSystemCountry (String countryCode);
}
