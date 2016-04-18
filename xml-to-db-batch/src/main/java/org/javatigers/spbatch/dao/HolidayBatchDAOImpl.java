package org.javatigers.spbatch.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.model.holiday.Country;
import org.javatigers.spbatch.model.holiday.Holiday;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayBatchDAOImpl implements HolidayBatchDAO {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public HolidayFile findHolidayFileByTypeAndDate(FileType fileType,
			DateTime dateTime) {
		String query = "SELECT hf FROM HolidayFile hf WHERE hf.type = :fileType AND hf.date = :dateTime ";
		logger.debug("===============toDate :::: {} =============", dateTime.toDate());
		TypedQuery<HolidayFile> typedQuery = em.createQuery(query, HolidayFile.class)
			.setParameter("fileType", fileType)
			.setParameter("dateTime", dateTime.toDate());
		
		return typedQuery.getSingleResult();
	}

	@Override
	public void createHolidayFile(HolidayFile holidayFile) {
		em.persist(holidayFile);

	}

	@Override
	public Country getSystemCountry(String countryCode) {
		String sql = "SELECT c FROM Country c WHERE c.code = :code";
		
		return em.createQuery(sql, Country.class).setParameter("code", countryCode).getSingleResult();
	}

	@Override
	public void createHoliday(Holiday holiday) {
		em.persist(holiday);

	}

	@Override
	public void updateHolidayFile(HolidayFile holidayFile) {
		em.merge(holidayFile);
	}

}
