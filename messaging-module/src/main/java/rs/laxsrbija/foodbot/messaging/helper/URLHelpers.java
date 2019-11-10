package rs.laxsrbija.foodbot.messaging.helper;

import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;
import rs.laxsrbija.foodbot.messaging.model.Message;
import rs.laxsrbija.foodbot.messaging.model.RegistrationToken;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URLHelpers
{
	public static String getLoginParametersURL()
	{
		final URIBuilder uriBuilder = getURIBuilder(SkypeConstants.API_LOGIN + "/oauth/microsoft");

		uriBuilder.addParameter("client_id", "578134");
		uriBuilder.addParameter("redirect_uri", "https://web.skype.com");

		return uriBuilder.toString();
	}

	public static String getLoginTokenURL()
	{
		final URIBuilder uriBuilder = getURIBuilder(SkypeConstants.API_MSACC);
		final String replyParameterValue = "https://lw.skype.com/login/oauth/proxy?client_id=578134%26site_name=lw.skype.com%26"
			+ "redirect_uri=https%3A%2F%2Fweb.skype.com%2F";

		uriBuilder.addParameter("wa", "wsignin1.0");
		uriBuilder.addParameter("wp", "MBI_SSL");
		uriBuilder.addParameter("wreply", replyParameterValue);

		return uriBuilder.toString();
	}

	public static String getSkypeTokenURL()
	{
		final URIBuilder uriBuilder = getURIBuilder(SkypeConstants.API_LOGIN + "/microsoft");

		uriBuilder.addParameter("client_id", "578134");
		uriBuilder.addParameter("redirect_uri", "https://web.skype.com");

		return uriBuilder.toString();
	}

	public static String getRegistrationTokenURL(final String messageHost)
	{
		final URIBuilder uriBuilder = getURIBuilder(messageHost + SkypeConstants.ENDPOINT_ADDRESS_PART_ENDPOINTS);
		return uriBuilder.toString();
	}

	public static String getMessagesURL(final RegistrationToken registrationToken, final Message message)
	{
		final URIBuilder uriBuilder = getURIBuilder(
			registrationToken.getMessageHost() +
				SkypeConstants.ENDPOINT_ADDRESS_PART_CONVERSATIONS +
				"/" + message.getGroupId() +
				"/messages");
		return uriBuilder.toString();
	}

	private static URIBuilder getURIBuilder(final String startingUrl)
	{
		try
		{
			return new URIBuilder(startingUrl);
		}
		catch (URISyntaxException e)
		{
			throw new FoodBotMessagingException("Malformed URL: ", e);
		}
	}
}
