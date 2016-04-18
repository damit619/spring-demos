package org.javatigers.spbatch.holiday.batch;

import java.io.File;
import java.net.URLDecoder;

import org.javatigers.spbatch.enums.HolidayType;
import org.javatigers.spbatch.enums.ModificationFlag;
import org.javatigers.spbatch.util.DateUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.ClassUtils;

import com.ximpleware.extended.VTDGenHuge;
import com.ximpleware.extended.VTDNavHuge;

public class HolidayFileReader extends
		AbstractItemCountingItemStreamItemReader<HolidayBatchVO> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HolidayFileReader.class);

	private static final String ENCODING_UTF8 = "utf-8";

	private String fragmentElementName;
	private String filePath;
	private boolean normalizeFilePath;

	private VTDGenHuge vtdGenHuge;
	private VTDNavHuge vtdNavHuge;

	private int currentIndex = -2;
	private boolean nextFragmentExist;

	public HolidayFileReader() {
		setName(ClassUtils.getShortName(HolidayFileReader.class));
	}

	/**
	 * @return the fragmentElementName
	 */
	public String getFragmentElementName() {
		return fragmentElementName;
	}

	public void setFragmentElementName(String fragmentElementName) {
		this.fragmentElementName = fragmentElementName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isNormalizeFilePath() {
		return normalizeFilePath;
	}

	/**
	 * @param normalizeFilePath
	 *            the normalizeFilePath to set
	 */
	public void setNormalizeFilePath(boolean normalizeFilePath) {
		this.normalizeFilePath = normalizeFilePath;
	}

	@Override
	protected HolidayBatchVO doRead() throws Exception {
		HolidayBatchVO holidayBatchVO = null;
		if (currentIndex > -2) {
			nextFragmentExist = vtdNavHuge.toElement(VTDNavHuge.NEXT_SIBLING);
		} else {
			nextFragmentExist = true;
		}
		currentIndex = vtdNavHuge.getCurrentIndex();
		if (nextFragmentExist && currentIndex > 0
				&& vtdNavHuge.matchElement(fragmentElementName)) {
			holidayBatchVO = new HolidayBatchVO();
			boolean nextElemExist = vtdNavHuge.toElement(VTDNavHuge.FC);
			if (nextElemExist) {
				do {
					readElement(holidayBatchVO);
					nextElemExist = vtdNavHuge
							.toElement(VTDNavHuge.NEXT_SIBLING);
				} while (nextElemExist);
			}
			vtdNavHuge.toElement(VTDNavHuge.PARENT);
		}
		return holidayBatchVO;
	}

	@Override
	protected void doOpen() throws Exception {
		vtdGenHuge = new VTDGenHuge();
		if (normalizeFilePath) {
			if (filePath.startsWith("file:")) {
				filePath = filePath.substring(5);
			}
			filePath = URLDecoder.decode(filePath, ENCODING_UTF8);
			filePath = new File(filePath).getPath();
		}
		if (vtdGenHuge.parseFile(filePath, true, VTDGenHuge.MEM_MAPPED)) {
			vtdNavHuge = vtdGenHuge.getNav();
			vtdNavHuge.toElement(VTDNavHuge.ROOT);
			vtdNavHuge.toElement(VTDNavHuge.FIRST_CHILD);
		} else {
			throw new Exception("parsing exception");
		}
		LOGGER.debug("HolidayFileReader finished reading file: " + filePath);
	}

	@Override
	protected void doClose() throws Exception {

	}

	private void populateBean(HolidayBatchVO holidayBatchVO,
			String elementName, String elementValue) {
		if ("tag".equals(elementName)) {
			holidayBatchVO.setTag(elementValue);
		}  else if (FlatFileContants.HOLIDAY_MODIFICATION_ELEMENT.equals(elementName)) {
			holidayBatchVO.setModificationFlag(ModificationFlag
					.valueOf(elementValue));
		} else if (FlatFileContants.HOLIDAY_COUNTRY_CODE_ELEMENT
				.equals(elementName)) {
			holidayBatchVO.setCountryCode(elementValue);
		} else if (FlatFileContants.HOLIDAY_COUNTRY_NAME_ELEMENT
				.equals(elementName)) {
			holidayBatchVO.setCountryName(elementValue);
		} else if (FlatFileContants.HOLIDAY_DATE_ELEMENT.equals(elementName)) {
			DateTime dateTime = DateUtil.convertStringToDateTime(
					elementValue, FlatFileContants.DATE_FORMAT);
			holidayBatchVO.setHolidayDate(dateTime);
		} else if (FlatFileContants.HOLIDAY_TYPE_ELEMENT.equals(elementName)) {
			holidayBatchVO.setHolidayType(HolidayType.valueOf(elementValue));
		} else if (FlatFileContants.HOLIDAY_INFO_ELEMENT.equals(elementName)) {
			holidayBatchVO.setHolidayInfo(elementValue);
		}
	}

	private void readElement(HolidayBatchVO holidayBatchVO) throws Exception {
		int elementPos = vtdNavHuge.getCurrentIndex();
		if (elementPos != -1) {
			String elementName = vtdNavHuge.toString(elementPos);
			int elementValueIndex = vtdNavHuge.getText();
			if (elementValueIndex != -1) {
				String elementVal = vtdNavHuge
						.toNormalizedString(elementValueIndex);

				// return when holiday type is not "H"
				/*if (!elementVal.equalsIgnoreCase(HolidayType.H.toString())) {
					return;
				}*/
				populateBean(holidayBatchVO, elementName, elementVal);

			}
		}
	}
}
