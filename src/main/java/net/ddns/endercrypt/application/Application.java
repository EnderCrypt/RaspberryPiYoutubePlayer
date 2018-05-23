package net.ddns.endercrypt.application;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.kohsuke.args4j.Option;

import net.ddns.endercrypt.mapper.ApplicationExceptionMapper;

public class Application
{
	private static Application instance = new Application();

	public static Application get()
	{
		return instance;
	}

	@Option(name = "-s", aliases = { "-script" }, required = true, usage = "The script that streams youtube")
	private File script;

	@Option(name = "-p", aliases = { "-port" }, required = false, usage = "The port to host the web view on")
	private int port;

	public void reset()
	{
		script = null;
		port = 8080;
	}

	private Application()
	{
		reset();
	}

	private static String WEB_PACKAGE = "net.ddns.endercrypt.web";

	private HttpServer httpServer;

	protected void main() throws IOException, InterruptedException, URISyntaxException
	{
		// text
		System.out.print("\u001b[2J"); // clear sceen
		System.out.flush();
		System.out.print("\u001b[H"); // move cursor to start
		System.out.flush();
		System.out.println(RaspberryPiYoutubePlayer.class.getSimpleName() + " by EnderCrypt (Magnus Gunnarsson)");

		// arg
		if (script.exists() == false)
		{
			throw new ApplicationException("script file not found (" + script + ")");
		}

		// web server
		URI baseUri = new URI("http://0.0.0.0:" + port + "/");
		ResourceConfig resourceConfig = new ResourceConfig().packages(WEB_PACKAGE);
		resourceConfig.register(new ApplicationExceptionMapper());
		httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig, false);
		httpServer.start();
	}

	public File getScript()
	{
		return script;
	}
}
