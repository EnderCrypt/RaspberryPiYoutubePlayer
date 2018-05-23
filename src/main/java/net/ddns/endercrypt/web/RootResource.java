package net.ddns.endercrypt.web;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RootResource
{
	private String REDIRECT_TARGET = "/home";

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response index() throws URISyntaxException
	{
		return Response.seeOther(new URI(REDIRECT_TARGET)).build();
	}
}
