package databox.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.services.core.AbstractServiceController;

@Singleton
public class MainControllerHolder {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private List<AbstractServiceController> controllersList = new ArrayList<>();

	private static MainControllerHolder instance;

	public static MainControllerHolder getInstance() {
		if (instance == null) {
			instance = new MainControllerHolder();
		}
		return instance;
	}

	public void addController(AbstractServiceController controller) {
		if (controller == null) {
			logger.error("Controller is null.");
			return;
		}

		if (cantainsController(controller.getFullId())) {
			logger.debug("Controller already exists and will not be added.");
			return;
		}

		logger.debug("Adding controller: " + controller.getFullId());
		controllersList.add(controller);
		if (!controller.isAlive()) {
			controller.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void removeController(AbstractServiceController controller) {
		if (controller == null) {
			logger.error("Controller is null.");
			return;
		}

		logger.debug("Removing controller: " + controller.getFullId());
		controllersList.remove(controller);
		controller.stop();
	}

	public void removeController(String fullId) {
		if (!cantainsController(fullId)) {
			logger.debug("Controller not present: " + fullId);
		}

		Optional<AbstractServiceController> isController = controllersList.stream().filter(c -> c.getFullId().equalsIgnoreCase(fullId)).findAny();

		if (isController.isPresent()) { // should be always true but just in case
			removeController(isController.get());
		}
	}

	public boolean cantainsController(String type, String userId) {
		return cantainsController(AbstractServiceController.createId(type, userId));
	}

	public boolean cantainsController(String fullId) {
		return controllersList.stream().anyMatch(c -> c.idMatheches(fullId));
	}

	public AbstractServiceController getController(String type, String userId) {
		Optional<AbstractServiceController> controllerEntity = controllersList.stream().filter(c -> c.idMatheches(AbstractServiceController.createId(type, userId))).findFirst();
		return controllerEntity.isPresent() ? controllerEntity.get() : null;
	}

	public int getControllersSize() {
		return controllersList.size();
	}

	public String printControllersInfo() {
		return controllersList.stream().map(c -> c.toString()).collect(Collectors.joining("\n"));
	}
}
