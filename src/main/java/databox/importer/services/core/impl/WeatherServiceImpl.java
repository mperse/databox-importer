package databox.importer.services.core.impl;

import java.text.DecimalFormat;

import databox.importer.MainControllerHolder;
import databox.importer.constants.MainConstants;
import databox.importer.services.core.resource.WeatherService;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.parcel.ParcelQueryUtil;
import databox.importer.services.weather.KoperWeatherServiceController;

public class WeatherServiceImpl implements WeatherService {

	DecimalFormat formatter = new DecimalFormat("###,###.00");

	DataboxWrapper databox = new DataboxWrapper(MainConstants.databoxParcelPushToken);

	ParcelQueryUtil util = new ParcelQueryUtil();

	String fullId = null;

	int delayInMinutes = 2;

	public WeatherServiceImpl() {
		// start service update on initialization
		MainControllerHolder.getInstance().addController(createController());
	}

	private KoperWeatherServiceController createController() {
		KoperWeatherServiceController controller = new KoperWeatherServiceController(delayInMinutes * 60000);
		fullId = controller.getFullId();// id doesn't change but its cleaner to use generated version instead of using a constant
		return controller;
	}

	@Override
	public String startDataUpdate() {
		// always create new controller. If it exists, it will not be added anyway.
		MainControllerHolder.getInstance().addController(createController());
		return "STARTED";
	}

	@Override
	public String stopDataUpdate() {
		MainControllerHolder.getInstance().removeController(fullId);
		return "STOPPED";
	}

}
