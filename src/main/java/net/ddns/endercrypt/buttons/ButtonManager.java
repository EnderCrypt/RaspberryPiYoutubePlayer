package net.ddns.endercrypt.buttons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class ButtonManager
{
	private static final File file = new File("buttons.txt");

	private static List<Button> buttons;

	static
	{
		try (InputStream input = new FileInputStream(file))
		{
			String buttonsTxt = IOUtils.toString(input, Charset.defaultCharset());
			buttons = Arrays.stream(buttonsTxt.split("\n")).map(s -> new Button(s)).collect(Collectors.toList());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static boolean hasCode(String code)
	{
		return buttons.stream().filter(b -> b.getCode().equals(code)).findAny().isPresent();
	}

	public static List<Button> getButtons()
	{
		return Collections.unmodifiableList(buttons);
	}

}
