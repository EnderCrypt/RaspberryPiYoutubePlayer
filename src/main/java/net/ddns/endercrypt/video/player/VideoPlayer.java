package net.ddns.endercrypt.video.player;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import net.ddns.endercrypt.video.Video;

public class VideoPlayer
{
	private VideoProcess videoProcess = null;

	private Set<VideoPlayerListener> listeners = new HashSet<>();

	public void addListener(VideoPlayerListener listener)
	{
		listeners.add(listener);
	}

	public boolean removeListener(VideoPlayerListener listener)
	{
		return listeners.remove(listener);
	}

	public void play(Video video) throws IOException
	{
		if (hasVideo())
		{
			throw new IllegalStateException("a video is already playing");
		}
		videoProcess = new VideoProcess(video);
		videoProcess.listen();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					int exitCode = videoProcess.getProcess().waitFor();
					stop();
					listeners.forEach(l -> l.onVideoStopped(exitCode));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		}).start();
	}

	public void stop()
	{
		requireVideo().stop();
		videoProcess = null;
	}

	public boolean hasVideo()
	{
		return (videoProcess != null);
	}

	public Optional<VideoProcess> getVideo()
	{
		return Optional.ofNullable(videoProcess);
	}

	public VideoProcess requireVideo()
	{
		return getVideo().orElseThrow(() -> new IllegalStateException("no video is currently playing"));
	}

	public void sendinput(String text) throws IOException
	{
		requireVideo().write(text);
	}
}
