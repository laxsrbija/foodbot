package rs.laxsrbija.foodbot.messaging.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.model.AbstractToken;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenLifetimeHelper
{
	private static final long RECOMMENDED_TOKEN_LIFESPAN_HOURS = 12L;

	public static boolean tokenHasExpired(final AbstractToken abstractToken)
	{
		final LocalDateTime now = LocalDateTime.now();
		return now.isAfter(abstractToken.getExpirationDate());
	}

	public static LocalDateTime getTokenExpiryDateTimeByLifetime(final long lifetimeInSeconds, final boolean useRecommendedLifespan)
	{
		final LocalDateTime now = LocalDateTime.now();
		final LocalDateTime tokenExpiryDateTime = now.plusSeconds(lifetimeInSeconds);

		if (useRecommendedLifespan)
		{
			// We should pick the shortest date between the one we recommend and the one provided
			log.info("Using a recommended Skype token lifetime");

			final LocalDateTime recommendedLifespan = now.plusHours(RECOMMENDED_TOKEN_LIFESPAN_HOURS);
			return tokenExpiryDateTime.isBefore(recommendedLifespan) ? tokenExpiryDateTime : recommendedLifespan;
		}

		return tokenExpiryDateTime;
	}

	public static LocalDateTime getTokenExpiryByTimestamp(final long timestamp, final boolean useRecommendedLifespan)
	{
		final LocalDateTime tokenExpiryDateTime =
			LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());

		if (useRecommendedLifespan)
		{
			// We should pick the shortest date between the one we recommend and the one provided
			log.info("Using a recommended Skype token lifetime");

			final LocalDateTime now = LocalDateTime.now();
			final LocalDateTime recommendedLifespan = now.plusHours(RECOMMENDED_TOKEN_LIFESPAN_HOURS);

			return tokenExpiryDateTime.isBefore(recommendedLifespan) ? tokenExpiryDateTime : recommendedLifespan;
		}

		return tokenExpiryDateTime;
	}
}
