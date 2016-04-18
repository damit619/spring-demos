package org.javatigers.spbatch.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HolidayFileDAOImpl extends GenericDAOImpl<HolidayFile, Long> implements HolidayFileDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public HolidayFileDAOImpl () {
			
		super(HolidayFile.class);
		
	}
	
	@Transactional
	@Override
	public HolidayFile findHolidayFileByTypeAndDate(FileType fileType,
			DateTime dateTime) {
		String query = "SELECT hf FROM HolidayFile hf WHERE hf.type = :fileType AND hf.date = :dateTime ";
		TypedQuery<HolidayFile> typedQuery = em.createQuery(query, HolidayFile.class)
			.setParameter("fileType", fileType)
			.setParameter("dateTime", dateTime);
		return typedQuery.getSingleResult();
	}
	
}
