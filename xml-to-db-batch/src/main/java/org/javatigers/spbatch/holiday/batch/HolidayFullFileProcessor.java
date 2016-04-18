package org.javatigers.spbatch.holiday.batch;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.javatigers.spbatch.dao.HolidayBatchDAO;
import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.enums.LoadStatus;
import org.javatigers.spbatch.model.holiday.Country;
import org.javatigers.spbatch.model.holiday.Holiday;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("step")
public class HolidayFullFileProcessor extends AbstractHolidayFileProcessor implements ItemProcessor<HolidayBatchVO, Holiday> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HolidayFullFileProcessor.class);

	private HolidayFile holidayFile;
	private final HolidayBatchDAO holidayBatchDao;

	@Inject
	public HolidayFullFileProcessor(HolidayBatchDAO holidayBatchDao) {
		this.holidayBatchDao = holidayBatchDao;
	}

	/**
	 * Create {@code HolidayFile} (once) and associate with {@code Holiday}.
	 * 
	 * @param coreBankDirectory
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Holiday process(HolidayBatchVO holidayBatchVO) throws Exception {
		LOGGER.debug("Start processing {}", HolidayBatchVO.class.getName());
		if (null == holidayFile) {
			holidayFile = new HolidayFile();
			holidayFile.setType(FileType.FULL);
			holidayFile.setDate(getDate());
			holidayFile.setStatus(LoadStatus.PARTIAL);

			HolidayFile holidayFileExist = null;
			try {
				holidayFileExist = this.holidayBatchDao.findHolidayFileByTypeAndDate(holidayFile.getType(), holidayFile.getDate());
			} catch (NoResultException e) {
				LOGGER.error(
						"HolidayBatchDAOImpl - getHolidayFile: Result not found for fileType, version");
			}
			if (null == holidayFileExist) {
				LOGGER.info(
						"Processing holiday full file records");
				
				//purge full file and delta file holidays except mannual entries for new file.
				//this.holidayBatchDao.purgeHolidays(holidayBatchVO.getModificationFlag());
				
				this.holidayBatchDao.createHolidayFile(holidayFile);
			} else {
				LOGGER.info(
						"Full holiday file already imported so ignoring file with");
				throw new Exception("Duplicate file");
			}
		} 
		Country country = null;
		Holiday holiday = null;
		try {
			country = this.holidayBatchDao.getSystemCountry(holidayBatchVO.getCountryCode());
			holiday = new Holiday();
			holiday.setHolidayFile(holidayFile);
			holiday.setModificationFlag(holidayBatchVO.getModificationFlag());
			holiday.setTag(holidayBatchVO.getTag());
			holiday.setDate(holidayBatchVO.getHolidayDate());
			holiday.setHolidayType(holidayBatchVO.getHolidayType());
			holiday.setHolidayInfo(holidayBatchVO.getHolidayInfo());
			holiday.setCountry(country);
			
		} catch (NoResultException e) {
			LOGGER.error(
					"Invalid country code {}",
					new Object[] { holidayBatchVO.getCountryCode() });
		}
		
		LOGGER.debug("Finished processing {}", Holiday.class.getName());

		return holiday;
	}

}

