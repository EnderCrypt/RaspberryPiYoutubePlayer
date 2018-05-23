package net.ddns.endercrypt.video;

import java.net.URL;
import java.util.Objects;

public class Video
{
	private URL url;

	public Video(URL url)
	{
		Objects.requireNonNull(url);
		this.url = url;
	}

	public URL getUrl()
	{
		return url;
	}

	public String toArgument()
	{
		return getUrl().toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Video other = (Video) obj;
		if (url == null)
		{
			if (other.url != null) return false;
		}
		else if (!url.equals(other.url)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return url.toString();
	}
}
