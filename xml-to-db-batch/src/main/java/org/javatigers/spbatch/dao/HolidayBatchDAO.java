package org.javatigers.spbatch.dao;

import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.model.holiday.Country;
import org.javatigers.spbatch.model.holiday.Holiday;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.joda.time.DateTime;

public interface HolidayBatchDAO {
	
	HolidayFile findHolidayFileByTypeAndDate(FileType fileType, DateTime dateTime);
	
	void createHolidayFile (HolidayFile holidayFile);
	
	Country getSystemCountry (String countryCode);
	
	void createHoliday (Holiday holiday);
	
	void updateHolidayFile (HolidayFile holidayFile);
}
