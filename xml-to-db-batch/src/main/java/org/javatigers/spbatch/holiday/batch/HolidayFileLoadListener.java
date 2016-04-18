package org.javatigers.spbatch.holiday.batch;

import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.model.holiday.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.stereotype.Component;

@Component
public class HolidayFileLoadListener implements SkipListener<HolidayBatchVO, Holiday> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HolidayFileLoadListener.class);

	@Override
	public void onSkipInProcess(HolidayBatchVO holidayBatchVO, Throwable processError) {
		LOGGER.error(
				"Failed to process record with modification flag ({}) due to error {}",
				new Object[] { holidayBatchVO.getModificationFlag(), processError },
				processError);
	}

	@Override
	public void onSkipInRead(Throwable readError) {
		LOGGER.error("Failed to read record due to error {}",
				new Object[] { readError }, readError);

	}

	@Override
	public void onSkipInWrite(Holiday holiday, Throwable writerError) {
		LOGGER.error(
				"Failed to insert record with modification flag ({}) due to error {}",
				new Object[] { holiday.getModificationFlag(), writerError },
				writerError);

	}

	/**
	 * This method runs when an step is executed and it takes the currently
	 * executing step which finished processing. We are logging step execution
	 * status if its complete and log an error if step execution status is not
	 * complete and skip count is > then 0.
	 * 
	 * @param stepExecution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		if (ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()) && stepExecution.getSkipCount() == 0) {
			
			String fileTypeName = stepExecution.getJobParameters().getString(
					FlatFileContants.FILE_TYPE);
			String fileDate = stepExecution.getJobParameters().getString(FlatFileContants.DATE);

			stepExecution
					.getJobExecution()
					.getExecutionContext()
					.put(FlatFileContants.MARK_AS_COMPLETE_FILE,
							FileType.valueOf(fileTypeName));
			stepExecution.getJobExecution().getExecutionContext()
					.putString(FlatFileContants.DATE, fileDate);
		} else {
			LOGGER.error(
					"File import incomplete for {}, {}",
					new Object[] {
							stepExecution.getJobParameters().getString(
									FlatFileContants.FILE_TYPE),
							stepExecution.getJobParameters().getString(
									FlatFileContants.DATE) });
		}
	}

}
