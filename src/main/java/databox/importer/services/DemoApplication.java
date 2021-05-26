package databox.importer.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.services.core.impl.FacebookServiceImpl;
import databox.importer.services.core.impl.ParcelDataServiceImpl;
import databox.importer.services.core.impl.SystemStatusImpl;
import databox.importer.services.core.impl.WeatherServiceImpl;
import databox.importer.utils.JsonObjectMapperProvider;

@ApplicationPath("/")
public class DemoApplication extends Application {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private LinkedHashSet<Class<?>> classes = new LinkedHashSet<>();

	public DemoApplication() {
		super();
		classes.add(JsonObjectMapperProvider.class);
		classes.add(ParcelDataServiceImpl.class);
		logger.info("Server Application started.");
	}

	@Override
	public Set<Object> getSingletons() {
		HashSet<Object> set = new HashSet<>();
		set.add(new SystemStatusImpl());
		set.add(new FacebookServiceImpl());
		set.add(new WeatherServiceImpl());
		return set;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return Collections.unmodifiableSet(classes);
	}

}
