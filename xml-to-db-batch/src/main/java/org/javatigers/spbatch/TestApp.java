package org.javatigers.spbatch;

import java.io.File;

import org.javatigers.spbatch.dao.CountryDAO;
import org.javatigers.spbatch.dao.HolidayDAO;
import org.javatigers.spbatch.dao.HolidayFileDAO;
import org.javatigers.spbatch.enums.FileType;
import org.javatigers.spbatch.enums.HolidayType;
import org.javatigers.spbatch.enums.ModificationFlag;
import org.javatigers.spbatch.model.holiday.Country;
import org.javatigers.spbatch.model.holiday.Holiday;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.joda.time.DateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/spring/spring-context.xml");
    	
		JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
		
		Job job = context.getBean("loadHolidayFullFile", Job.class);
		
		File inputFile = new File ("src/main/resources/META-INF/holiday/HOLIDAY_20150227.xml"); 
		
    	JobParameters jobParameters = 
        	    new JobParametersBuilder().addString("inputFile", inputFile.getAbsoluteFile().toURI().toString())
        	    .addString("fileType", FileType.FULL.name())
        	    .addString("date", "20150227")
        	    .toJobParameters();
    	try {
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Job Exit Status : "+ execution.getStatus());
 
        } catch (JobExecutionException e) {
            System.out.println("Job ExamResult failed");
            e.printStackTrace();
        }
    	
    	System.out.println( "Hello World!" );

	}
	
	
	
	
	private static void dbTest(ApplicationContext context) throws Exception {
		HolidayDAO holidayDAO = context.getBean("holidayDAOImpl", HolidayDAO.class);
    	CountryDAO countryDAO = context.getBean("countryDAOImpl", CountryDAO.class);
    	HolidayFileDAO holidayFileDAO = context.getBean("holidayFileDAOImpl", HolidayFileDAO.class);
    	
    	HolidayFile holidayFile = new HolidayFile();
    	holidayFile.setDate(new DateTime());
    	holidayFile.setName("holiday");
    	holidayFile.setType(FileType.FULL);
    	holidayFileDAO.create(holidayFile);
    	
    	Country country = new Country ();
    	country.setCode("NO");
    	country.setName("Norway");
    	countryDAO.create(country);
    	
    	Holiday holiday = new Holiday ();
    	
    	holiday.setCountry(country);
    	holiday.setHolidayFile(holidayFile);
    	holiday.setTag("HF");
    	holiday.setDate(new DateTime ());
    	holiday.setModificationFlag(ModificationFlag.A);
    	holiday.setHolidayType(HolidayType.H);
    	holidayDAO.create(holiday);
	}
}
