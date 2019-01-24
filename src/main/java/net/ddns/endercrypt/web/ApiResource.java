package net.ddns.endercrypt.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.ddns.endercrypt.buttons.ButtonManager;
import net.ddns.endercrypt.misc.UrlCleaner;
import net.ddns.endercrypt.resource.ResourceException;
import net.ddns.endercrypt.video.Video;
import net.ddns.endercrypt.video.VideoManager;
import net.ddns.endercrypt.video.player.VideoPlayer;
import net.ddns.endercrypt.video.player.VideoProcess;

@Path("/api")
public class ApiResource
{
	@POST
	@Path("/video/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response video_add(@FormParam("url") String urlString) throws ResourceException, IOException
	{
		if (VideoManager.add(UrlCleaner.clean(new URL(urlString))))
		{
			return Response.ok().build();
		}
		else
		{
			return Response.notModified().build();
		}
	}

	@GET
	@Path("/video/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response video_list() throws ResourceException
	{
		List<Video> videos = VideoManager.getVideos();
		return Response.ok(videos).build();
	}

	@GET
	@Path("/buttons")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buttons() throws FileNotFoundException, IOException
	{
		return Response.ok(ButtonManager.getButtons()).build();
	}

	@GET
	@Path("/console")
	@Produces(MediaType.TEXT_PLAIN)
	public Response console() throws ResourceException
	{
		VideoPlayer videoPlayer = VideoManager.getVideoPlayer();
		Optional<VideoProcess> videoProcessOptional = videoPlayer.getVideo();
		if (videoProcessOptional.isPresent())
		{
			String output = videoProcessOptional.get().getOuputString();
			return Response.ok(output).build();
		}
		else
		{
			return Response.noContent().build();
		}
	}

	@POST
	@Path("/console")
	@Produces(MediaType.TEXT_PLAIN)
	public Response console_write(@FormParam("code") String code) throws ResourceException, IOException
	{
		if (ButtonManager.hasCode(code) == false)
		{
			return Response.status(Status.BAD_REQUEST).build();
		}

		VideoPlayer videoPlayer = VideoManager.getVideoPlayer();
		Optional<VideoProcess> videoProcessOptional = videoPlayer.getVideo();
		if (videoProcessOptional.isPresent())
		{
			videoPlayer.sendinput(code);
			return Response.ok().build();
		}
		else
		{
			return Response.noContent().build();
		}
	}
}
