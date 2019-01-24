package net.ddns.endercrypt.misc.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

import net.ddns.endercrypt.misc.Cleaner;
import net.ddns.endercrypt.misc.UrlCleaner;

public class YoutubeCleaner implements Cleaner
{
	@Override
	public String getHost()
	{
		return "www.youtube.com";
	}

	@Override
	public URL clean(URL url) throws MalformedURLException
	{
		List<Entry<String, String>> queries = UrlCleaner.getQueries(url);

		queries.removeIf(new Predicate<Entry<String, String>>()
		{
			@Override
			public boolean test(Entry<String, String> entry)
			{
				return entry.getKey().equalsIgnoreCase("v") == false;
			}
		});

		String stringQueries = UrlCleaner.toQuery(queries);

		return new URL(url.getProtocol() + "://" + url.getHost() + url.getPath() + stringQueries);
	}
}
