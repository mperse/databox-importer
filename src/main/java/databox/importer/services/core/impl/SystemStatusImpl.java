package databox.importer.services.core.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import databox.importer.services.core.resource.SystemStatusService;

public class SystemStatusImpl implements SystemStatusService {

	@Context
	ServletContext context;

	@Context
	protected HttpServletRequest request;

	public SystemStatusImpl() {
	}

	@Override
	public Response status() {
		return Response.ok().entity("Server running.").build();
	}

}
