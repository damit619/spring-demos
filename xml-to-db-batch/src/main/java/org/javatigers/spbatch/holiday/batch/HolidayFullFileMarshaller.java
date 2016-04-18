package org.javatigers.spbatch.holiday.batch;

import org.javatigers.spbatch.enums.HolidayType;
import org.javatigers.spbatch.enums.ModificationFlag;
import org.javatigers.spbatch.model.holiday.HolidayFile;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;



import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

@Component
@Scope("step")
public class HolidayFullFileMarshaller extends XStreamMarshaller {
	
	/**
	 * @see HolidayBatchVO properties names.
	 */
	private static final String MODIFICATION_FLAG = "modificationFlag";
	private static final String HOLIDAY_TYPE = "holidayType";
	private static final String HOLIDAY_INFO = "holidayInfo";
	private static final String HOLIDAY_DATE = "holidayDate";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String COUNTRY_NAME = "countryName";
	
	/**
	 * Method to return {@see HolidayBatchVO}
	 * 
	 * @return {@see HolidayBatchVO}
	 */
	protected Class<?> getModelClass() {
		return HolidayBatchVO.class;
	}
	
	/**
     *Customize XStream and map file properties to xml elements.
     *
     * @param xstream
     */
    protected void customizeXStream(XStream xstream) {
    	xstream.registerConverter(new DateConverter(FlatFileContants.DATE_FORMAT, new String[] {}));
    	getXStream ().alias(FlatFileContants.HOLIDAY_DATA_EXPORT_ELEMENT, HolidayFile.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_ELEMENT, getModelClass ());
    	getXStream ().alias("tag", String.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_MODIFICATION_ELEMENT, ModificationFlag.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_COUNTRY_CODE_ELEMENT, String.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_COUNTRY_NAME_ELEMENT, String.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_DATE_ELEMENT, DateTime.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_TYPE_ELEMENT, HolidayType.class);
    	getXStream ().alias(FlatFileContants.HOLIDAY_INFO_ELEMENT, String.class);
    	
    	getXStream().aliasField(FlatFileContants.HOLIDAY_MODIFICATION_ELEMENT, getModelClass (), MODIFICATION_FLAG);
    	getXStream().aliasField("tag", getModelClass (), "tag");
    	getXStream().aliasField(FlatFileContants.HOLIDAY_COUNTRY_CODE_ELEMENT, getModelClass (), COUNTRY_CODE);
    	getXStream().aliasField(FlatFileContants.HOLIDAY_COUNTRY_NAME_ELEMENT, getModelClass (), COUNTRY_NAME);
    	getXStream().aliasField(FlatFileContants.HOLIDAY_DATE_ELEMENT, getModelClass (), HOLIDAY_DATE);
    	getXStream().aliasField(FlatFileContants.HOLIDAY_TYPE_ELEMENT, getModelClass (), HOLIDAY_TYPE);
    	getXStream().aliasField(FlatFileContants.HOLIDAY_INFO_ELEMENT, getModelClass (), HOLIDAY_INFO);
    	
    }
}

