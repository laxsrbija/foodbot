package rs.laxsrbija.foodbot.messaging.provider;

import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.FIELD_TOKEN_NAME;
import java.time.LocalDateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.configuration.MessagingServiceConfiguration;
import rs.laxsrbija.foodbot.messaging.helper.URLHelpers;
import rs.laxsrbija.foodbot.messaging.helper.Utils;
import rs.laxsrbija.foodbot.messaging.model.SkypeToken;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkypeTokenProvider
{
	private static final String FIELD_CLIENT_ID = "client_id";
	private static final String FIELD_OAUTH_PARTNER = "oauthPartner";
	private static final String FIELD_SITE_NAME = "site_name";
	private static final String FIELD_REDIRECT_URI = "redirect_uri";
	private static final String FIELD_TOKEN = "skypetoken";
	private static final String FIELD_EXPIRY = "expires_in";
	private static final long RECOMMENDED_TOKEN_LIFESPAN_HOURS = 12L;

	private final MessagingServiceConfiguration _messagingServiceConfiguration;

	public SkypeToken getSkypeToken(final String loginToken)
	{
		final HttpResponse<String> stringHttpResponse =
			Unirest.post(URLHelpers.getSkypeTokenURL())
				.field(FIELD_TOKEN_NAME, loginToken)
				.field(FIELD_CLIENT_ID, "578134")
				.field(FIELD_OAUTH_PARTNER, "999")
				.field(FIELD_SITE_NAME, "lw.skype.com")
				.field(FIELD_REDIRECT_URI, "https://web.skype.com").asString();

		final String body = stringHttpResponse.getBody();
		return parseResponseHtml(body);
	}

	private SkypeToken parseResponseHtml(final String html)
	{
		final Document document = Jsoup.parse(html);

		final String token = getInputFieldValue(document, FIELD_TOKEN);
		final long tokenLifetime = Long.parseLong(getInputFieldValue(document, FIELD_EXPIRY));

		return SkypeToken.builder()
			.token(token)
			.expiry(getTokenExpiryDateTime(tokenLifetime)).build();
	}

	private LocalDateTime getTokenExpiryDateTime(final long lifetimeInSeconds)
	{
		final LocalDateTime now = LocalDateTime.now();
		final LocalDateTime tokenExpiryDateTime = now.plusSeconds(lifetimeInSeconds);

		if (_messagingServiceConfiguration.isShorterTokenLifespan())
		{
			// We should pick the shortest date between the one we recommend and the one provided
			log.info("We are using a recommended Skype token lifetime");
			final LocalDateTime recommendedLifespan = now.plusHours(RECOMMENDED_TOKEN_LIFESPAN_HOURS);
			return tokenExpiryDateTime.isBefore(recommendedLifespan) ? tokenExpiryDateTime : recommendedLifespan;
		}

		return tokenExpiryDateTime;
	}

	private static String getInputFieldValue(final Document document, final String fieldName)
	{
		final Element element = document.select("input[name=" + fieldName + "]").first();
		return Utils.getInputFieldValue(element);
	}
}
