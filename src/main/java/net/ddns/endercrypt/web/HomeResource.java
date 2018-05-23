package net.ddns.endercrypt.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.ddns.endercrypt.resource.ResourceException;

@Path("/home")
public class HomeResource
{
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response home() throws ResourceException
	{
		return RawResource.getResponse("home.html");
	}
}
