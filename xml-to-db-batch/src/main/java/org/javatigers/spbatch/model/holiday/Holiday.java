package org.javatigers.spbatch.model.holiday;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.javatigers.spbatch.enums.HolidayType;
import org.javatigers.spbatch.enums.ModificationFlag;
import org.joda.time.DateTime;

@Entity
@Table(name = "holiday")
public class Holiday extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5970051468944846522L;
	
	@Column(name = "holiday_tag")
	private String tag;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "modification_flag")
	private ModificationFlag modificationFlag;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "holiday_type")
	private HolidayType holidayType;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private Country country;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "holiday_file_id")
	private HolidayFile holidayFile;
	
	@Column(name = "hoiday_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;
	
	@Column(name = "holiday_info")
	private String holidayInfo;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public ModificationFlag getModificationFlag() {
		return modificationFlag;
	}

	public void setModificationFlag(ModificationFlag modificationFlag) {
		this.modificationFlag = modificationFlag;
	}

	public HolidayType getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(HolidayType holidayType) {
		this.holidayType = holidayType;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public HolidayFile getHolidayFile() {
		return holidayFile;
	}

	public void setHolidayFile(HolidayFile holidayFile) {
		this.holidayFile = holidayFile;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public String getHolidayInfo() {
		return holidayInfo;
	}

	public void setHolidayInfo(String holidayInfo) {
		this.holidayInfo = holidayInfo;
	}
	
	
}
