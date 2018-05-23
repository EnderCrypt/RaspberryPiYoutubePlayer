package net.ddns.endercrypt.video.player;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.ddns.endercrypt.application.Application;
import net.ddns.endercrypt.video.Video;

public class VideoProcess
{
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Video video;
	private File scriptFile;

	private Process process;

	private InputStream inputStream;
	private OutputStream outputStream;

	private ByteArrayOutputStream output = new ByteArrayOutputStream(256);

	public VideoProcess(Video video) throws IOException
	{
		// init
		this.video = video;
		scriptFile = Application.get().getScript();

		// process
		ProcessBuilder processBuilder = new ProcessBuilder(scriptFile.getAbsoluteFile().toString(), video.toArgument());
		process = processBuilder.start();

		// streams
		inputStream = process.getInputStream();
		outputStream = process.getOutputStream();
	}

	public Video getVideo()
	{
		return video;
	}

	public Process getProcess()
	{
		return process;
	}

	public void write(String text) throws IOException
	{
		outputStream.write(text.getBytes());
		outputStream.flush();
	}

	public boolean isAlive()
	{
		return process.isAlive();
	}

	public void listen()
	{
		// read blocking
		executorService.execute(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						int num = inputStream.read();
						if (num == -1)
						{
							break;
						}
						output.write(num);
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public String getOuputString()
	{
		return output.toString();
	}

	public void stop()
	{
		process.destroy();
	}
}
