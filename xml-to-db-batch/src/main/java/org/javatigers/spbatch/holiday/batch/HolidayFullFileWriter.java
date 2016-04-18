package org.javatigers.spbatch.holiday.batch;

import java.util.List;

import javax.inject.Inject;

import org.javatigers.spbatch.dao.HolidayBatchDAO;
import org.javatigers.spbatch.enums.ModificationFlag;
import org.javatigers.spbatch.model.holiday.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("step")
public class HolidayFullFileWriter implements ItemWriter<Holiday> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HolidayFullFileWriter.class);
	private final HolidayBatchDAO holidayBatchDao;

	@Inject
	public HolidayFullFileWriter(HolidayBatchDAO holidayBatchDao) {
		this.holidayBatchDao = holidayBatchDao;
	}

	/**
	 * Insert {@code Holiday} to database.
	 * 
	 * @param holidays
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void write(List<? extends Holiday> holidays) throws Exception {
		LOGGER.debug("Start writing file");

		for (Holiday holiday : holidays) {
			if (null != holiday && ModificationFlag.A.equals(holiday.getModificationFlag())) {
				this.holidayBatchDao.createHoliday(holiday);
			}
		}

		LOGGER.debug("Finished writing file");
	}

}

