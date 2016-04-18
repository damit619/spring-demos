package org.javatigers.spbatch.model.holiday;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.enums.LoadStatus;
import org.joda.time.DateTime;

@Entity
@Table(name = "holiday_file")
public class HolidayFile extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8985668220432546361L;
	
	@Column(name = "file_name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "file_type")
	private FileType type;
	
	@Column(name = "file_date", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "load_status")
	private LoadStatus status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public LoadStatus getStatus() {
		return status;
	}

	public void setStatus(LoadStatus status) {
		this.status = status;
	}
	
}
