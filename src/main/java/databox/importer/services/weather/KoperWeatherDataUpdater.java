package databox.importer.services.weather;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.constants.DataboxKeys;
import databox.importer.constants.MainConstants;
import databox.importer.entity.weather.arso.Data;
import databox.importer.entity.weather.arso.Data.MetData;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.core.sdk.KPI;
import databox.importer.utils.ConnectionUtil;

public class KoperWeatherDataUpdater {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd MMMM yyyy HH:mm:SSZ", Locale.getDefault());

	DataboxWrapper databox = new DataboxWrapper(MainConstants.databoxTemperaturePushToken);

	/**
	 * Makes call to ARSO meteo weather service and pushes air and weather data in Koper to Databox
	 */
	public KoperWeatherDataUpdater() {
	}

	public void updateData() throws Exception {
		databox.pushDataAndLog(data2Kpi(getDataFromService().getMetData()));
	}

	private List<KPI> data2Kpi(MetData metData) throws Exception {
		logger.debug(metData.getT() + " " + metData.getTVarUnit() + ", temperatura vode: " + metData.getTw() + " " + metData.getTwVarUnit());

		Date date = new Date();
		List<KPI> data = new ArrayList<>(2);
		data.add(new KPI().setKey(DataboxKeys.TEMPERATURE.AIR).setValue(metData.getT()).setDate(date));
		data.add(new KPI().setKey(DataboxKeys.TEMPERATURE.WATER).setValue(metData.getTw()).setDate(date));
		return data;
	}

	public Data getDataFromService() throws Exception {
		ConnectionUtil util = new ConnectionUtil();
		String data = util.simpleGetRequest(new SimpleRequestParams(MainConstants.arsoServiceUrl));
		System.out.println(data);

		JAXBContext jaxbContext;
		String tt = new String(data.getBytes(), "ISO-8859-1");
		ByteArrayInputStream bis = new ByteArrayInputStream(tt.getBytes("UTF-8"));
		jaxbContext = JAXBContext.newInstance(Data.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Data) jaxbUnmarshaller.unmarshal(bis);
	}

}
