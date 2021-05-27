package databox.importer.services.weather;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.constants.MainConstants;
import databox.importer.services.core.AbstractServiceController;

@Singleton
public class KoperWeatherServiceController extends AbstractServiceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	KoperWeatherDataUpdater updater = new KoperWeatherDataUpdater();

	public static final String TYPE = "weather";
	int numFailedUpdates = 0;

	private Integer customDelay;

	public KoperWeatherServiceController(Integer customDelay) {
		super(TYPE, "Koper");
		this.customDelay = customDelay;
	}

	@Override
	public synchronized void run() {
		logger.info("Strating update: " + getFullId());
		try {
			updater.updateData();
			numFailedUpdates = 0;
		} catch (Exception e) {
			numFailedUpdates++;
			if (numFailedUpdates > 5) {
				disableAndRemove();
			}
		}
		try {
			wait(customDelay != null ? customDelay : MainConstants.DEFAULT_THREAD_DELAY);
			run();
		} catch (Exception e) {
			logger.error("failed to delay thread: " + e.getLocalizedMessage(), e);
		}
	}
}
