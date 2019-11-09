package rs.laxsrbija.foodbot.messaging.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import kong.unirest.Header;
import kong.unirest.Headers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils // TODO Refactor
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

	public static String getInputFieldValue(final Element element)
	{
		if (element == null)
		{
			throw new FoodBotMessagingException("Unable to find element in the DOM");
		}

		final String inputToken = element.val();

		if (inputToken.isEmpty())
		{
			throw new FoodBotMessagingException("Value of the input field is empty");
		}

		return inputToken;
	}

	public static LocalDateTime localDateTimeFromTimestamp(long timestamp, boolean milliseconds)
	{
		final Instant instant = milliseconds ? Instant.ofEpochMilli(timestamp) : Instant.ofEpochSecond(timestamp);
		return LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
	}
}
