package databox.importer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.services.core.sdk.DataboxWrapper;

public class DateFormatUtil {

	private static final Logger logger = LoggerFactory.getLogger(DataboxWrapper.class);

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	public static final SimpleDateFormat SDF_DAY_ONLY = new SimpleDateFormat("yyyy-MM-dd 0:0:0", Locale.getDefault());

	public static Date getCurrentDay() {
		try {
			return SDF_DAY_ONLY.parse(SDF_DAY_ONLY.format(new Date()));
		} catch (ParseException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
