package databox.importer.services.weather;

import databox.importer.utils.ConnectionQueryParams;

public class SimpleRequestParams implements ConnectionQueryParams {

	private String url;

	public SimpleRequestParams(String url) {
		this.url = url;
	}

	@Override
	public String buildRequestPath() {
		return url;
	}

}
