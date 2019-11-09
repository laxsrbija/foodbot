package rs.laxsrbija.foodbot.messaging.provider;

import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.API_HOST;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.configuration.MessagingServiceConfiguration;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;
import rs.laxsrbija.foodbot.messaging.helper.*;
import rs.laxsrbija.foodbot.messaging.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationTokenProvider
{
	private static final String HEADER_LOCK_AND_KEY = "LockAndKey";
	private static final String HEADER_AUTHENTICATION = "Authentication";
	private static final String HEADER_BEHAVIOR_OVERRIDE = "BehaviorOverride";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_LOCATION = "Location";
	private static final String HEADER_SET_REGISTRATION_TOKEN = "Set-RegistrationToken";
	private static final Pattern LOCATION_HEADER_PATTERN =
		Pattern.compile("(https://[^/]+/v1/)users/ME/endpoints(/(\\{[a-zA-Z0-9\\-]+}))?");
	private static final RegistrationTokenRequest AGENT_REGISTRATION_TOKEN_REQUEST =
		new RegistrationTokenRequest("Agent");

	private final MessagingServiceConfiguration _messagingServiceConfiguration;

	public RegistrationToken getRegistrationToken(final SkypeToken skypeToken)
	{
		if (TokenLifetimeHelper.skypeTokenHasExpired(skypeToken))
		{
			throw new FoodBotMessagingException("Unable to get the registration token, as the Skype token has expired");
		}

		final RegistrationToken registrationToken = new RegistrationToken(API_HOST);

		while (registrationToken.getToken() == null)
		{
			final String messageHost = URLHelpers.getRegistrationTokenURL(registrationToken.getMessageHost());
			final HttpResponse<JsonNode> jsonNodeHttpResponse =
				Unirest.post(messageHost)
					.header(HEADER_LOCK_AND_KEY, getLockAndKeyHeaderValue())
					.header(HEADER_AUTHENTICATION, "skypetoken=" + skypeToken.getToken())
					.header(HEADER_BEHAVIOR_OVERRIDE, "redirectAs404")
					.header(HEADER_CONTENT_TYPE, "application/json")
					.body(AGENT_REGISTRATION_TOKEN_REQUEST)
					.asJson();

			final Map<String, String> headers = Utils.getHeaders(jsonNodeHttpResponse.getHeaders());

			final String locationHeader = headers.get(HEADER_LOCATION);
			if (shouldReRegisterToDifferentHost(registrationToken, locationHeader))
			{
				// Skype requires the use of a different host.
				// A new registration request will be sent to the host provided in the Location header.
				continue;
			}

			final String registrationTokenHeader = headers.get(HEADER_SET_REGISTRATION_TOKEN);
			setRegistrationToken(registrationToken, registrationTokenHeader);

			if (registrationToken.getEndpoint() == null
				&& jsonNodeHttpResponse.getStatus() == 200
				&& jsonNodeHttpResponse.getBody() != null)
			{
				final JsonNode body = jsonNodeHttpResponse.getBody();
				extractEndpointFromResponseBody(registrationToken, body);
			}
		}

		return registrationToken;
	}

	private static void extractEndpointFromResponseBody(final RegistrationToken registrationToken, final JsonNode body)
	{
		final JSONArray array = body.getArray();

		if (array.isEmpty())
		{
			throw new FoodBotMessagingException("The JSON array returned in a response body is empty");
		}

		final JSONObject jsonObject = array.getJSONObject(0);
		final String newEndpoint = jsonObject.getString("id");

		registrationToken.setEndpoint(newEndpoint);
	}

	private void setRegistrationToken(final RegistrationToken registrationToken, final String registrationTokenHeaderValue)
	{
		if (registrationTokenHeaderValue == null)
		{
			return;
		}

		RegistrationTokenHeaderHelpers.updateRegistrationToken(registrationToken, registrationTokenHeaderValue);
		RegistrationTokenHeaderHelpers.updateRegistrationTokenEndpoint(registrationToken, registrationTokenHeaderValue);
		RegistrationTokenHeaderHelpers.updateRegistrationTokenExpiration(
			registrationToken,
			registrationTokenHeaderValue,
			_messagingServiceConfiguration.isShorterTokenLifespan());
	}

	private static boolean shouldReRegisterToDifferentHost(
		final RegistrationToken registrationToken,
		final String locationHeaderValue)
	{
		if (locationHeaderValue != null)
		{
			final String decodedLocationHeaderValue = decodeURLValue(locationHeaderValue);
			final Matcher matcher = LOCATION_HEADER_PATTERN.matcher(decodedLocationHeaderValue);

			if (matcher.find())
			{
				final String receivedEndpoint = matcher.group(3);
				if (receivedEndpoint != null)
				{
					registrationToken.setEndpoint(receivedEndpoint);
				}

				final String newMessageHost = matcher.group(1);
				if (newMessageHost != null && !newMessageHost.equals(registrationToken.getMessageHost()))
				{
					registrationToken.setMessageHost(newMessageHost);
					return true;
				}
			}
		}

		return false;
	}

	private static String decodeURLValue(final String locationHeaderValue)
	{
		try
		{
			return URLDecoder.decode(locationHeaderValue, StandardCharsets.UTF_8.name());
		}
		catch (final UnsupportedEncodingException e)
		{
			throw new FoodBotMessagingException("Unable to decode the provided URL: " + locationHeaderValue);
		}
	}

	private static String getLockAndKeyHeaderValue()
	{
		final String epochSeconds = String.valueOf(Instant.now().getEpochSecond());
		final String mac256Hash = HashHelper.getMac256Hash(epochSeconds);

		return String.format("appId=msmsgs@msnmsgr.com; time=%s; lockAndKeyResponse=%s", epochSeconds, mac256Hash);
	}

	private static class RegistrationTokenHeaderHelpers
	{
		private static final Pattern REGISTRATION_TOKEN_PATTERN = Pattern.compile("(registrationToken=[a-zA-Z0-9+/=]+)");
		private static final Pattern EXPIRATION_PATTERN = Pattern.compile("expires=([0-9]+)");
		private static final Pattern ENDPOINT_PATTERN = Pattern.compile("endpointId=(\\{[a-zA-Z0-9\\\\\\-]+})");

		static void updateRegistrationToken(
			final RegistrationToken registrationToken,
			final String registrationTokenHeaderValue)
		{
			final String newToken = getMatchedValue(REGISTRATION_TOKEN_PATTERN, registrationTokenHeaderValue);
			registrationToken.setToken(newToken);
		}

		static void updateRegistrationTokenExpiration(
			final RegistrationToken registrationToken,
			final String registrationTokenHeaderValue,
			final boolean useRecommendedLifespan)
		{
			final String matchedValue = getMatchedValue(EXPIRATION_PATTERN, registrationTokenHeaderValue);

			if (matchedValue != null)
			{
				final long expiration = Long.parseLong(matchedValue);
				final LocalDateTime tokenExpiryDateTime =
					TokenLifetimeHelper.getTokenExpiryByTimestamp(expiration, useRecommendedLifespan);

				registrationToken.setExpirationDate(tokenExpiryDateTime);
			}
		}

		static void updateRegistrationTokenEndpoint(
			final RegistrationToken registrationToken,
			final String registrationTokenHeaderValue)
		{
			final String expiration = getMatchedValue(ENDPOINT_PATTERN, registrationTokenHeaderValue);
			registrationToken.setEndpoint(expiration);
		}

		private static String getMatchedValue(final Pattern pattern, final String content)
		{
			final Matcher matcher = pattern.matcher(content);

			if (matcher.find())
			{
				return matcher.group(1);
			}

			return null;
		}
	}
}
