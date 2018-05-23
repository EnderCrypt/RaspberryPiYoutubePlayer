package net.ddns.endercrypt.resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

import net.ddns.endercrypt.application.Application;

public class ResourceReader
{
	private static final File resourceFolder = Application.get().getResourceFolder();	
	
	public static byte[] readBytes(String path) throws ResourceException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try
		{
			InputStream input = null;
			if (resourceFolder == null)
			{
				input = ResourceReader.class.getResourceAsStream(path);
			}
			else
			{
				input = new FileInputStream(new File(resourceFolder.toString() + "/" + path));
			}
			if (input == null)
			{
				throw new ResourceUnavailableException("cant find resource: " + path);
			}
			IOUtils.copy(input, output);
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
