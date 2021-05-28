package databox.importer.services.core.impl;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.MainControllerHolder;
import databox.importer.services.core.AbstractServiceController;
import databox.importer.services.core.resource.FacebookService;
import databox.importer.services.fb.FbServiceController;

public class FacebookServiceImpl implements FacebookService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public FacebookServiceImpl() {
	}

	@Override
	public String addUserDataUpdater(String userId, String authToken) {
		if (userId == null || userId.isEmpty() || authToken == null || authToken.isEmpty()) {
			throw new BadRequestException("Parameter 'userId' and 'authToken' must be specified!");
		}

		try {
			AbstractServiceController existingController = MainControllerHolder.getInstance().getController(FbServiceController.TYPE, userId);
			if (existingController != null) {
				((FbServiceController) existingController).updateAuthToken(authToken);
			} else {
				logger.info("Adding new controller.");
				MainControllerHolder.getInstance().addController(new FbServiceController(userId, authToken));
			}
			return "OK";
		} catch (Exception e) {
			String msg = "Failed to execute update: " + e.getLocalizedMessage();
			logger.error(msg, e);
			throw new InternalServerErrorException(e.getLocalizedMessage());
		}

	}

	@Override
	public String removeUserDataUpdater(String userId) {
		AbstractServiceController controller = MainControllerHolder.getInstance().getController(FbServiceController.TYPE, userId);

		if (controller != null) {
			controller.disableAndRemove();
			return "OK";
		} else {
			throw new BadRequestException("No controller exists for the selected userId.");
		}
	}

}
