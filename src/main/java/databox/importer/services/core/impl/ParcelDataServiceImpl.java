package databox.importer.services.core.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.constants.DataboxKeys;
import databox.importer.constants.MainConstants;
import databox.importer.entity.parcel.Parcela;
import databox.importer.services.core.resource.ParcelDataService;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.core.sdk.KPI;
import databox.importer.services.parcel.ParcelQueryUtil;
import databox.importer.utils.DateFormatUtil;
import databox.importer.utils.StringUtil;

public class ParcelDataServiceImpl implements ParcelDataService {

	DecimalFormat formatter = new DecimalFormat("###,###.00");

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	DataboxWrapper databox = new DataboxWrapper(MainConstants.databoxParcelPushToken);

	ParcelQueryUtil util = new ParcelQueryUtil();

	public ParcelDataServiceImpl() {
	}

	@Override
	public String getParcelValue(String kosifko, String parcel) {

		if (StringUtil.isNullOrEmpty(kosifko)) {
			throw new BadRequestException("Please enter valid cadastral number.");
		}

		if (StringUtil.isNullOrEmpty(parcel)) {
			throw new BadRequestException("Please enter valid parcel number.");
		}

		logger.debug("kosifko: " + kosifko + ", parcel" + parcel);
		Parcela parcela = util.getParcel(kosifko.trim(), parcel.trim());
		if (parcela == null) {
			throw new BadRequestException("No parcelexists for given parameters.");
		}

		logger.debug("Parcel info:" + parcela);
		BigDecimal totalVal = util.getParcelValue(parcela.getPcMid());
		try {
			generateFakeDataboxReport(parcela, totalVal);
		} catch (Exception e) {
			logger.error("Failed to log data do databox: " + e.getLocalizedMessage(), e);
		}
		return totalVal != null ? formatter.format(totalVal) + " €" : "/";
	}

	private void generateFakeDataboxReport(Parcela parc, BigDecimal totalVal) throws ParseException, Exception {
		databox.pushDataAndLog(getValues(totalVal, parc.getPovrsina()));

		// also push information about parcel data request
		KPI requestKpi = new KPI().setKey(DataboxKeys.PARCEL.PARCEL_QUERY_INFO).setValue(1).setDate(new Date());
		databox.pushDataAndLog(Collections.singletonList(requestKpi));
	}

	public List<KPI> getValues(BigDecimal totalVal, BigDecimal area) throws ParseException {
		List<KPI> data = new ArrayList<>();
		Date dateFrom = DateFormatUtil.SDF.parse("2021-05-1 00:00:00");
		Date dateTo = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(dateFrom);
		while (c.getTime().compareTo(dateTo) <= 0) {
			data.add(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_SIZE).setValue(getFactor(0.2) * area.doubleValue()).setDate(c.getTime()).setUnit("m2"));
			data.add(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_VALUE).setValue(getFactor(0.2) * totalVal.doubleValue()).setDate(c.getTime()).setUnit("�"));
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return data;
	}

	private static double getFactor(Double varialePart) {
		return (ThreadLocalRandom.current().nextDouble() * varialePart) + (1 - varialePart);
	}

}
