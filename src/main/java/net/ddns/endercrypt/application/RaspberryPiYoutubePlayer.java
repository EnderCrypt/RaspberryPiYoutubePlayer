package net.ddns.endercrypt.application;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import net.ddns.endercrypt.application.Application;
import net.ddns.endercrypt.application.ApplicationException;

public class RaspberryPiYoutubePlayer
{
	public static void main(String[] args)
	{
		Application application = Application.get();
		application.reset();
		CmdLineParser parser = new CmdLineParser(application);
		try
		{
			parser.parseArgument(args);
			application.main();
		}
		catch (ApplicationException | CmdLineException e)
		{
			File file = new File(RaspberryPiYoutubePlayer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			application.reset();
			System.err.println("Error: " + e.getMessage());
			System.err.println("java -jar " + file.getName() + " [Options]");
			parser.printUsage(System.err);
		}
		catch (Exception e)
		{
			application.reset();
			System.err.println("Fatal exception!");
			e.printStackTrace();
		}
	}

}
