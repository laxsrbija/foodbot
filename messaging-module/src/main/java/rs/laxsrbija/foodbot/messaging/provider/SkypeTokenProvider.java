package rs.laxsrbija.foodbot.messaging.provider;

import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.FIELD_TOKEN_NAME;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.messaging.helper.URLHelpers;
import rs.laxsrbija.foodbot.messaging.helper.Utils;
import rs.laxsrbija.foodbot.messaging.model.SkypeToken;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkypeTokenProvider
{
	private static final String FIELD_CLIENT_ID = "client_id";
	private static final String FIELD_OAUTH_PARTNER = "oauthPartner";
	private static final String FIELD_SITE_NAME = "site_name";
	private static final String FIELD_REDIRECT_URI = "redirect_uri";
	private static final String FIELD_TOKEN = "skypetoken";
	private static final String FIELD_EXPIRY = "expires_in";

	public static SkypeToken getSkypeToken(final String loginToken)
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

	private static SkypeToken parseResponseHtml(final String html)
	{
		final Document document = Jsoup.parse(html);

		final String token = getInputFieldValue(document, FIELD_TOKEN);
		final String expiry = getInputFieldValue(document, FIELD_EXPIRY);

		return SkypeToken.builder()
			.token(token)
			.expiry(expiry).build();
	}

	private static String getInputFieldValue(final Document document, final String fieldName)
	{
		final Element element = document.select("input[name=" + fieldName + "]").first();
		return Utils.getInputFieldValue(element);
	}
}
