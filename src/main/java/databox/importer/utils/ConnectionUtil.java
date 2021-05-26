package databox.importer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConnectionUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final int CONNECT_TIMEOUT = 10000;
	private static final int SERVER_TIMEOUT = 10000;
	private ObjectMapper mapper = new ObjectMapper();

	public ConnectionUtil() {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public String simpleGetRequest(ConnectionQueryParams requestParams) {
		InputStream responseReader = null;
		try {
			try {
				URL url = new URL(requestParams.buildRequestPath());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				setConnectionProperties(conn);
				// writeHeaders(conn, prepareHeaders(requestParams));

				logger.debug("Connecting to: " + url.toString());
				if (conn.getResponseCode() >= 300) {
					String msg = "Error: " + conn.getResponseCode() + (conn.getErrorStream() != null ? IOUtils.toString(conn.getErrorStream(), "UTF-8") : "Connection or error stream is null.");
					logger.error(msg);
					throw new IOException("Failed to load data, HTTP code: " + conn.getResponseCode());
				}
				return printStreamData(conn.getInputStream());

			} finally {
				if (responseReader != null) {
					responseReader.close();
				}
			}
		} catch (Exception e) {
			logger.error("Error when querying data: " + e.getLocalizedMessage(), e);
		}
		return null;
	}

	public <T> T executeGetRequest(ConnectionQueryParams requestParams, Class<T> valueType) {
		InputStream responseReader = null;
		try {
			try {
				URL url = new URL(requestParams.buildRequestPath());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				setConnectionProperties(conn);
				// writeHeaders(conn, prepareHeaders(requestParams));

				logger.debug("Connecting to: " + url.toString());
				return readResponse(conn, valueType, "Cannot execute GET request: " + url);
			} finally {
				if (responseReader != null) {
					responseReader.close();
				}
			}
		} catch (Exception e) {
			logger.error("Error when querying data: " + e.getLocalizedMessage(), e);
		}
		return null;
	}

	public <T> List<T> executeGetRequestList(ConnectionQueryParams requestParams, Class<T> valueType) {
		InputStream responseReader = null;
		try {
			try {
				URL url = new URL(requestParams.buildRequestPath());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				setConnectionProperties(conn);
				// writeHeaders(conn, prepareHeaders(requestParams));

				logger.debug("Connecting to: " + url.toString());
				return readResponseList(conn, valueType, "Cannot execute GET request: " + url);
			} finally {
				if (responseReader != null) {
					responseReader.close();
				}
			}
		} catch (Exception e) {
			logger.error("Error when querying data: " + e.getLocalizedMessage(), e);
		}
		return null;
	}

	/*
	 * private Map<String, String> prepareHeaders() { Map<String, String> httpHeaders = new HashMap<>(); return httpHeaders; }
	 */

	private <T> T readResponse(HttpURLConnection conn, Class<T> valueType, String errorMsg) throws IOException, JsonParseException, JsonMappingException {
		if (conn.getResponseCode() >= 300) {
			String msg = "Error: " + conn.getResponseCode() + (conn.getErrorStream() != null ? IOUtils.toString(conn.getErrorStream(), "UTF-8") : "Connection or error stream is null.");
			logger.error(msg);
			throw new IOException(errorMsg + ", HTTP code: " + conn.getResponseCode());
		}
		return mapper.readValue(printStreamData(conn.getInputStream()), valueType);
	}

	private <T> List<T> readResponseList(HttpURLConnection conn, Class<T> valueType, String errorMsg) throws IOException, JsonParseException, JsonMappingException {
		if (conn.getResponseCode() >= 300) {
			String msg = "Error: " + conn.getResponseCode() + (conn.getErrorStream() != null ? IOUtils.toString(conn.getErrorStream(), "UTF-8") : "Connection or error stream is null.");
			logger.error(msg);
			throw new IOException(errorMsg + ", HTTP code: " + conn.getResponseCode());
		}

		return mapper.readValue(printStreamData(conn.getInputStream()), mapper.getTypeFactory().constructCollectionType(List.class, valueType));
	}

	private String printStreamData(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String resp = sb.toString();
		logger.debug("Response: ");
		logger.debug(resp);
		return resp;
	}

	private void setConnectionProperties(HttpURLConnection conn) {
		conn.setUseCaches(false);
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(SERVER_TIMEOUT);
		conn.setDoOutput(true);
		conn.setDoInput(true);

		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Cache-Control", "no-cache");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	}

}
