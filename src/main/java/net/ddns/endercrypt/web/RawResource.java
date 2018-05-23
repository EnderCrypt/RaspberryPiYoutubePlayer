package net.ddns.endercrypt.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import net.ddns.endercrypt.resource.ResourceException;
import net.ddns.endercrypt.resource.ResourceReader;

@Path("/raw/{any: .*}")
public class RawResource
{
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response home(@PathParam("any") List<PathSegment> segments) throws ResourceException
	{
		String path = segments.stream().map(p -> p.getPath()).collect(Collectors.joining("/"));
		return getResponse(path);
	}

	public static String get(String path) throws ResourceException
	{
		return ResourceReader.readString("/web/" + path);
	}

	public static Response getResponse(String path) throws ResourceException
	{
		return Response.ok(get(path)).build();
	}
}
