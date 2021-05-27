package databox.importer.services.core.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Path(ResourceBase.WEATHER_PATH)
public interface WeatherService {

	@GET
	@Path("/startService/")
	public String startDataUpdate();

	@GET
	@Path("/stopService/")
	public String stopDataUpdate();

}
