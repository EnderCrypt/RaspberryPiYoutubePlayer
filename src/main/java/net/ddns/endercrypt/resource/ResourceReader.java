package net.ddns.endercrypt.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class ResourceReader
{
	public static byte[] readBytes(String path) throws ResourceException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try
		{
			try (InputStream input = ResourceReader.class.getResourceAsStream(path))
			{
				if (input == null)
				{
					throw new ResourceUnavailableException("cant find resource: " + path);
				}
				IOUtils.copy(input, output);
			}
		}
		catch (IOException e)
		{
			throw new ResourceException(e);
		}
		return output.toByteArray();
	}

	public static String readString(String path) throws ResourceException
	{
		return new String(readBytes(path));
	}
}
