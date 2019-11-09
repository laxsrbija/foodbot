package rs.laxsrbija.foodbot.core.service;

import java.util.*;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.persistence.model.ConfigurationKeys;
import rs.laxsrbija.foodbot.persistence.model.entity.ConfigurationEntry;
import rs.laxsrbija.foodbot.persistence.model.entity.DailyMenu;
import rs.laxsrbija.foodbot.persistence.service.ConfigurationService;

@Service
@RequiredArgsConstructor
public class PlaceholderService
{
	private static final String MAIN_COURSE = "MAIN_COURSE";
	private static final String SALAD = "SALAD";

	private final ConfigurationService _configurationService;

	public String substituteReminderPlaceholders(final DailyMenu dailyMenu)
	{
		final Optional<ConfigurationEntry> reminderMessage =
			_configurationService.getConfiguration(ConfigurationKeys.DAILY_REMINDER_MESSAGE);

		if (reminderMessage.isPresent())
		{
			final Map<String, String> placeholderValues = getPlaceholderValues(dailyMenu);
			return StringSubstitutor.replace(reminderMessage.get().getConfigurationValue(), placeholderValues);
		}

		return null;
	}

	private Map<String, String> getPlaceholderValues(final DailyMenu dailyMenu)
	{
		final Map<String, String> placeholderMap = new HashMap<>();
		final Set<ConfigurationEntry> configurations = _configurationService.getConfigurations();

		for (final ConfigurationEntry configurationEntry : configurations)
		{
			final String configurationKey = configurationEntry.getConfigurationKey().name();
			final String configurationValue = configurationEntry.getConfigurationValue();
			placeholderMap.put(configurationKey, configurationValue);
		}

		placeholderMap.put(MAIN_COURSE, dailyMenu.getMainCourse());
		placeholderMap.put(SALAD, dailyMenu.getSalad());

		return placeholderMap;
	}
}
