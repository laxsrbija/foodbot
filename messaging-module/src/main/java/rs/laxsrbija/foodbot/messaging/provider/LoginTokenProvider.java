package rs.laxsrbija.foodbot.messaging.provider;

import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.COOKIE_CKTST;
import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.COOKIE_MSPOK;
import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.COOKIE_MSPREQU;
import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.FIELD_PPFT;
import static rs.laxsrbija.foodbot.messaging.helper.SkypeConstants.FIELD_TOKEN_NAME;
import java.util.Date;
import java.util.StringJoiner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.messaging.configuration.MessagingServiceConfiguration;
import rs.laxsrbija.foodbot.messaging.helper.URLHelpers;
import rs.laxsrbija.foodbot.messaging.helper.Utils;
import rs.laxsrbija.foodbot.messaging.model.LoginParameters;

@Service
@RequiredArgsConstructor
public class LoginTokenProvider
{
	private static final String HEADER_COOKIE = "cookie";
	private static final String FIELD_LOGIN = "login";
	private static final String FIELD_PASSWORD = "passwd";

	private final MessagingServiceConfiguration _messagingServiceConfiguration;

	public String getLoginToken(final LoginParameters loginParameters)
	{
		final String username = _messagingServiceConfiguration.getUsername();
		final String password = _messagingServiceConfiguration.getPassword();

		final String ppftField = loginParameters.getPpftField();

		final HttpResponse<String> stringHttpResponse = Unirest.post(URLHelpers.getLoginTokenURL())
			.header(HEADER_COOKIE, buildCookieHeader(loginParameters))
			.field(FIELD_LOGIN, username)
			.field(FIELD_PASSWORD, password)
			.field(FIELD_PPFT, ppftField).asString();

		final String httpBody = stringHttpResponse.getBody();
		return parseLoginToken(httpBody);
	}

	private String buildCookieHeader(final LoginParameters loginParameters)
	{
		final StringJoiner stringJoiner = new StringJoiner("; ");

		stringJoiner.add(COOKIE_MSPREQU + "=" + loginParameters.getMsprequCookie());
		stringJoiner.add(COOKIE_MSPOK + "=" + loginParameters.getMspokCookie());
		stringJoiner.add(COOKIE_CKTST + "=" + getLoginTokenTimestamp());

		return stringJoiner.toString();
	}

	private String getLoginTokenTimestamp()
	{
		final long timestampInMilliseconds = new Date().getTime();
		return "G" + timestampInMilliseconds;
	}

	private String parseLoginToken(final String html)
	{
		final Document document = Jsoup.parse(html);
		final Element tokenInput = document.getElementById(FIELD_TOKEN_NAME);

		return Utils.getInputFieldValue(tokenInput);
	}
}
