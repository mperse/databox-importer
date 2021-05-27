package databox.importer.services.core.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ResourceBase {

	public static final String SYSTEM_PATH = "system/";
	public static final String FB_PATH = "fb/";
	public static final String PARCEL_PATH = "parcel/";
	public static final String WEATHER_PATH = "weather/";

}
