package org.javatigers.spbatch.holiday.batch;

	public interface FlatFileContants {
		
		/**
		 * Reusable holiday xml elements constants.
		 */
		String HOLIDAY_DATA_EXPORT_ELEMENT = "dataexport";
		String HOLIDAY_ELEMENT = "holiday";
		String HOLIDAY_MODIFICATION_ELEMENT = "modification_flag";
		String HOLIDAY_TYPE_ELEMENT = "holyday_type";
		String HOLIDAY_INFO_ELEMENT = "special_holiday_info";
		String HOLIDAY_DATE_ELEMENT = "date";
		String HOLIDAY_COUNTRY_CODE_ELEMENT = "country_code";
		String HOLIDAY_COUNTRY_NAME_ELEMENT = "country_name";
		
		String DATE_FORMAT = "yyyyMMdd";
		
		String MARK_AS_COMPLETE_FILE = "fileTypeToMarkComplete";
		String MARK_VERSION_AS_COMPLETE = "versionToMarkComplete";
		String FILE_TYPE = "fileType";
		String VERSION = "version";
		String DATE = "date";
}	
