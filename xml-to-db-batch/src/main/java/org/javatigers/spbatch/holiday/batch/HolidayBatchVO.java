package org.javatigers.spbatch.holiday.batch;

import org.javatigers.spbatch.enums.HolidayType;
import org.javatigers.spbatch.enums.ModificationFlag;
import org.joda.time.DateTime;

public class HolidayBatchVO {

	private ModificationFlag modificationFlag;
	private String tag;
	private String countryCode;
	private String countryName;
	private DateTime holidayDate;
	private HolidayType holidayType;
	private String holidayInfo;

	public ModificationFlag getModificationFlag() {
		return modificationFlag;
	}

	public void setModificationFlag(ModificationFlag modificationFlag) {
		this.modificationFlag = modificationFlag;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public DateTime getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(DateTime holidayDate) {
		this.holidayDate = holidayDate;
	}

	public HolidayType getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(HolidayType holidayType) {
		this.holidayType = holidayType;
	}

	public String getHolidayInfo() {
		return holidayInfo;
	}

	public void setHolidayInfo(String holidayInfo) {
		this.holidayInfo = holidayInfo;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}

