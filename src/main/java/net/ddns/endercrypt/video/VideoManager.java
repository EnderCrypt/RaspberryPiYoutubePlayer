package net.ddns.endercrypt.video;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import net.ddns.endercrypt.video.player.VideoPlayer;
import net.ddns.endercrypt.video.player.VideoPlayerListener;

public class VideoManager
{
	private static VideoPlayer videoPlayer = new VideoPlayer();

	private static Queue<Video> queue = new ArrayDeque<>();

	static
	{
		videoPlayer.addListener(new VideoPlayerListener()
		{
			@Override
			public void onVideoStopped(int exitCode)
			{
				// System.out.println("Exited: " + exitCode);
				if (queue.isEmpty() == false)
				{
					Video video = queue.remove();
					try
					{
						getVideoPlayer().play(video);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static VideoPlayer getVideoPlayer()
	{
		return videoPlayer;
	}

	public static boolean add(URL url) throws IOException
	{
		Video video = new Video(url);

		if (queue.contains(video))
		{
			return false;
		}

		if (getVideoPlayer().hasVideo())
		{
			queue.add(video);
		}
		else
		{
			getVideoPlayer().play(video);
		}
		return true;
	}

	public static List<Video> getVideos()
	{
		return Collections.unmodifiableList(new ArrayList<>(queue));
	}

}
