package org.javatigers.spbatch.dao;

import org.javatigers.spbatch.model.holiday.Holiday;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayDAOImpl extends GenericDAOImpl<Holiday, Long> implements HolidayDAO {
	
	public HolidayDAOImpl () {
			
		super(Holiday.class);
		
	}
	
}
