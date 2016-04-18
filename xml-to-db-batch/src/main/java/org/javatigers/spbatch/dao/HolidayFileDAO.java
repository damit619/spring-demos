package org.javatigers.spbatch.dao;

import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.joda.time.DateTime;

public interface HolidayFileDAO extends GenericDAO<HolidayFile, Long> {
	
	HolidayFile findHolidayFileByTypeAndDate (FileType fileType, DateTime dateTime);
}
