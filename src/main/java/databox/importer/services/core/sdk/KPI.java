package databox.importer.services.core.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import databox.importer.constants.MainConstants;

public class KPI {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	ObjectMapper mapper = new ObjectMapper();

	private static SimpleDateFormat SDF;

	private String key;
	private Object value;
	private Date date;
	private String unit;
	private Map<String, Object> attributes = new HashMap<>();

	static {
		SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SDF.setTimeZone(MainConstants.DEFAULT_TIME_ZONE);
	}

	public KPI() {
	}

	public String getKey() {
		return key;
	}

	public KPI setKey(String key) {
		this.key = key;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public KPI setValue(Object value) {
		this.value = value;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public KPI setDate(Date date) {
		this.date = date;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public KPI setUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public KPI addAttribute(String key, Object value) {
		attributes.put(key, value);
		return this;
	}

	public KPI addAttributes(Map<String, Object> attributes) {
		if (attributes != null) {
			this.attributes.putAll(attributes);
		}
		return this;
	}

	public void removeAttribute(String key) {
		if (attributes.containsKey(key)) {
			attributes.remove(key);
		}
	}

	public void clearAttributes() {
		attributes.clear();
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		String json = " { \"$" + key + "\": " + getJsonValue();
		if (date != null) {
			json += ", \"date\": \"" + SDF.format(date) + "\"";
		}
		if (unit != null) {
			json += ", \"unit\": \"" + unit + "\"";
		}
		for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
			json += ", \"" + attribute.getKey() + "\": \"" + attribute.getValue() + "\"";
		}
		json += "}";
		return json;
	}

	private String getJsonValue() {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return null;
	}
}
