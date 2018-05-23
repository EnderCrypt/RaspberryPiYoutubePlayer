package net.ddns.endercrypt.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang.exception.ExceptionUtils;

import net.ddns.endercrypt.resource.ResourceException;
import net.ddns.endercrypt.web.RawResource;

public class ApplicationExceptionMapper implements ExceptionMapper<Exception>
{
	@Override
	public Response toResponse(Exception exception)
	{
		String stackTrace = ExceptionUtils.getFullStackTrace(exception);

		ResponseBuilder response = Response.serverError();
		try
		{
			// properly display exception
			String exceptionHtml = RawResource.get("exception.html");
			exceptionHtml = exceptionHtml.replace("[FULL_STACK_TRACE]", stackTrace);
			response = response.entity(exceptionHtml);
		}
		catch (ResourceException e)
		{
			// failed to properly display exception, fallback
			response = response.entity(stackTrace);
		}

		return response.build();
	}
}
