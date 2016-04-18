package org.javatigers.spbatch.holiday.batch;

import org.javatigers.spbatch.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;


public abstract class AbstractHolidayFileProcessor {
	
	private static final String DATE_FORMAT = "yyyyMMdd";
			
	private String name;
	private String fileType;
	private String date;
	
	public String getName() {
		return name;
	}
	@Value("#{jobParameters['name']}")
	public void setName(String name) {
		this.name = name;
	}
	public String getFileType() {
		return fileType;
	}
	@Value("#{jobParameters['fileType']}")
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public DateTime getDate() {
		return DateUtil.convertStringToDateTime(this.date, DATE_FORMAT);
	}
	@Value("#{jobParameters['date']}")
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
		
}
