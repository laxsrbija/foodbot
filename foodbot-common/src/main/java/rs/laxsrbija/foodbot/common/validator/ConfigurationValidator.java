package rs.laxsrbija.foodbot.common.validator;

import static rs.laxsrbija.foodbot.common.service.ConfigurationService.REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.configuration.DefaultPlaceholderConfiguration;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.model.entity.ConfigurationEntity;

@Component
@RequiredArgsConstructor
public class ConfigurationValidator
{
	private static final Pattern KEY_PATTERN = Pattern.compile("^[a-zA-Z_]+$");

	private final DefaultPlaceholderConfiguration _configuration;

	public void validateConfiguration(final ConfigurationEntity configurationEntity)
	{
		validateNullKeyAndValue(configurationEntity);
		validateKeyName(configurationEntity.getKey());
		validateNotificationTextSelfReference(configurationEntity);
		validateReservedKeyNameUsage(configurationEntity.getKey());
	}

	private void validateNullKeyAndValue(final ConfigurationEntity configurationEntity)
	{
		if (configurationEntity.getKey() == null || configurationEntity.getValue() == null)
		{
			throw new FoodBotException("Configuration key and value cannot be null");
		}
	}

	private void validateKeyName(final String key)
	{
		final Matcher matcher = KEY_PATTERN.matcher(key);
		if (!matcher.matches())
		{
			throw new FoodBotException("Configuration key can only comprise of alphabet characters and underscores");
		}
	}

	private void validateNotificationTextSelfReference(final ConfigurationEntity configurationEntity)
	{
		final String key = configurationEntity.getKey().toUpperCase();
		if (REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY.equals(key))
		{
			final String value = configurationEntity.getValue();
			if (value.toUpperCase().contains(key))
			{
				throw new FoodBotException("Cannot self-reference a notification text key.");
			}
		}
	}

	private void validateReservedKeyNameUsage(final String key)
	{
		if (key.equalsIgnoreCase(_configuration.getGreeting().getKey())
			|| key.equalsIgnoreCase(_configuration.getMainCourse().getKey())
			|| key.equalsIgnoreCase(_configuration.getSalad().getKey()))
		{
			throw new FoodBotException("Cannot use a reserved configuration key name");
		}
	}
}
