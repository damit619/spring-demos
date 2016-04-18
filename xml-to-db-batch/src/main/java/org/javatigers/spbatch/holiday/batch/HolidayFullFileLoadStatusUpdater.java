package org.javatigers.spbatch.holiday.batch;


import javax.inject.Inject;

import org.javatigers.spbatch.dao.HolidayBatchDAO;
import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.enums.LoadStatus;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.javatigers.spbatch.util.DateUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class HolidayFullFileLoadStatusUpdater implements Tasklet {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HolidayFullFileLoadStatusUpdater.class);

	private final HolidayBatchDAO holidayBatchDAO;

	@Inject
	public HolidayFullFileLoadStatusUpdater(HolidayBatchDAO holidayBatchDAO) {
		this.holidayBatchDAO = holidayBatchDAO;
	}

	/**
	 * method to find file status as complete and if file status found with
	 * version and filetype set its status as lodded.
	 */
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		FileType fileType = (FileType) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext()
					.get(FlatFileContants.MARK_AS_COMPLETE_FILE);
		if (null != fileType) {
			
			String fileDate =  chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext()
					.getString(FlatFileContants.DATE);
			DateTime dateTime = DateUtil.convertStringToDateTime(fileDate, FlatFileContants.DATE_FORMAT);
			
			HolidayFile holidayFile = null;
			try {
				holidayFile = this.holidayBatchDAO.findHolidayFileByTypeAndDate(fileType, dateTime);
			} catch (EmptyResultDataAccessException e) {
				throw new RuntimeException("No holiday file found");
			}
			holidayFile.setStatus(LoadStatus.LOADED);
			this.holidayBatchDAO.updateHolidayFile(holidayFile);
			LOGGER.info(
					"File import COMPLETE");
		}
		return RepeatStatus.FINISHED;
	}

}

