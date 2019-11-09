package rs.laxsrbija.foodbot.messaging.provider;

import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.COOKIE_MSPOK;
import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.COOKIE_MSPREQU;
import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.FIELD_PPFT;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kong.unirest.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;
import rs.laxsrbija.foodbot.messaging.helper.URLHelpers;
import rs.laxsrbija.foodbot.messaging.helper.Utils;
import rs.laxsrbija.foodbot.messaging.model.LoginParameters;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginParametersProvider
{
	private static final Pattern PPFT_PATTERN = Pattern.compile("<input.+name=\"" + FIELD_PPFT + "\".+value=\"(.*)\".*/>");

	public static LoginParameters getLoginParameters()
	{
		final GetRequest getRequest = Unirest.get(URLHelpers.getLoginParametersURL());
		final HttpResponse<String> stringHttpResponse = getRequest.asString();

		final String body = stringHttpResponse.getBody();
		final String ppftValue = getPPFTValue(body);

		final Headers headers = stringHttpResponse.getHeaders();
		final Map<String, String> cookies = Utils.getCookies(headers);

		return LoginParameters.builder()
			.ppftField(ppftValue)
			.mspokCookie(getCookieValue(COOKIE_MSPOK, cookies))
			.msprequCookie(getCookieValue(COOKIE_MSPREQU, cookies)).build();
	}

	private static String getPPFTValue(final String html)
	{
		final Matcher matcher = PPFT_PATTERN.matcher(html);

		if (matcher.find())
		{
			return matcher.group(1);
		}

		throw new FoodBotMessagingException("Unable to find the value of the hidden field PPFT");
	}

	private static String getCookieValue(final String cookieName, final Map<String, String> cookies)
	{
		final String cookie = cookies.get(cookieName);

		if (cookie == null)
		{
			throw new FoodBotMessagingException("Unable to find cookie " + cookieName);
		}

		return cookie;
	}
}
