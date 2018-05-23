package net.ddns.endercrypt.buttons;

public class Button
{
	private String name;
	private String code;

	public Button(String configString)
	{
		String[] split = configString.split("=");
		this.name = split[0];
		this.code = split[1];
		trim();
	}

	public Button(String name, String code)
	{
		this.name = name;
		this.code = code;
		trim();
	}

	private void trim()
	{
		this.name = name.trim();
		this.code = code.trim();
	}

	public String getName()
	{
		return name;
	}

	public String getCode()
	{
		return code;
	}

}
