package databox.importer.services.core.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Path(ResourceBase.FB_PATH)
public interface FacebookService {

	@GET
	@Path("/user/{userId}/initDataUpdater/{authToken}")
	public String addUserDataUpdater(@PathParam("userId") String userId, @PathParam("authToken") String authToken);

	@GET
	@Path("/user/{userId}/stopDataUpdater/")
	public String removeUserDataUpdater(@PathParam("userId") String userId);
}
