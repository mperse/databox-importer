package databox.importer.services.weather;

import static org.junit.Assert.fail;

import org.junit.Test;

public class WeatherDataUpdaterTest {

	@Test
	public void test1() {
		KoperWeatherDataUpdater updater = new KoperWeatherDataUpdater();
		try {
			updater.getDataFromService();
		} catch (Exception e) {
			fail("Obtaining data from service should not fail: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void test2() {
		KoperWeatherDataUpdater updater = new KoperWeatherDataUpdater();
		try {
			updater.updateData();
		} catch (Exception e) {
			fail("Updating weather data should not fail: " + e.getLocalizedMessage());
		}
	}

}
