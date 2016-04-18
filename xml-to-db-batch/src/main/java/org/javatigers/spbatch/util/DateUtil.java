package org.javatigers.spbatch.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtil {
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	
	public static String convertDateTimeToString (DateTime dateTime, String format) {
		if (null == format) {
			format = DATE_FORMAT;
		}
		return  DateTimeFormat.forPattern(format).print(dateTime);
	}
	
	public static DateTime convertStringToDateTime (String strDate, String format) {
		if (null == format) {
			format = DATE_FORMAT;
		}
		return  DateTimeFormat.forPattern(format).parseDateTime(strDate);
	}
}
