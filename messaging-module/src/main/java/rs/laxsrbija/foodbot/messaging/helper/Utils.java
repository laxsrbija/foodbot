package rs.laxsrbija.foodbot.messaging.helper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kong.unirest.Header;
import kong.unirest.Headers;

public class Utils
{
	private static final Pattern COOKIE_PATTERN = Pattern.compile("([a-zA-Z]*)=(.*?);");

	public static Map<String, String> getCookies(final Headers headers)
	{
		final List<String> cookies = headers.get("Set-Cookie");
		final Map<String, String> cookieMap = new HashMap<>();

		for (String cookie : cookies)
		{
			final Matcher cookieMatcher = COOKIE_PATTERN.matcher(cookie);
			if (cookieMatcher.find())
			{
				cookieMap.put(cookieMatcher.group(1), cookieMatcher.group(2));
			}
		}

		return cookieMap;
	}

	public static Map<String, String> getHeaders(final Headers headers)
	{
		final Map<String, String> headerMap = new HashMap<>();

		for (final Header header : headers.all())
		{
			headerMap.put(header.getName(), header.getValue());
		}

		return headerMap;
	}
}
