package net.ddns.endercrypt.misc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.ddns.endercrypt.misc.impl.YoutubeCleaner;

public class UrlCleaner
{
	private static List<Cleaner> cleaners = new ArrayList<>();
	static
	{
		cleaners.add(new YoutubeCleaner());
	}

	public static URL clean(URL url) throws MalformedURLException
	{
		for (Cleaner cleaner : cleaners)
		{
			if (cleaner.getHost().equalsIgnoreCase(url.getHost()))
			{
				return cleaner.clean(url);
			}
		}
		return url;
	}

	public static List<Entry<String, String>> getQueries(URL url)
	{
		List<Entry<String, String>> results = new ArrayList<>();

		for (String query : url.getQuery().split("\\&"))
		{
			String[] split = query.split("\\&");
			String key = split[0];
			String value = split[1];
			results.add(new AbstractMap.SimpleEntry<>(key, value));
		}

		return results;
	}

	public static String toQuery(List<Entry<String, String>> queries)
	{
		StringBuilder sb = new StringBuilder();
		if (queries.size() > 0)
		{
			sb.append("?");
		}
		Iterator<Entry<String, String>> iterator = queries.iterator();
		while (iterator.hasNext())
		{
			Entry<String, String> entry = iterator.next();
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			if (iterator.hasNext())
			{
				sb.append("&");
			}
		}
		return sb.toString();
	}
}
