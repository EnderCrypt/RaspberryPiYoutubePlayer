package net.ddns.endercrypt.misc;

import java.net.MalformedURLException;
import java.net.URL;

public interface Cleaner
{
	public String getHost();

	public URL clean(URL url) throws MalformedURLException;
}
