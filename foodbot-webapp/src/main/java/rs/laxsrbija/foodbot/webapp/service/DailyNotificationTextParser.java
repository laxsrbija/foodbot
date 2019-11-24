package rs.laxsrbija.foodbot.webapp.service;

import static rs.laxsrbija.foodbot.common.service.ConfigurationService.REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.configuration.DefaultPlaceholderConfiguration;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.model.entity.*;
import rs.laxsrbija.foodbot.common.service.*;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class DailyNotificationTextParser
{
	private final MenuService _menuService;
	private final GreetingService _greetingService;
	private final ConfigurationService _configurationService;
	private final DefaultPlaceholderConfiguration _defaultPlaceholderConfiguration;

	public String parseNotificationText()
	{
		final MenuEntity menu = _menuService.getMenuForToday()
			.orElseThrow(() -> new ResourceNotFoundException("Unable to fetch today's menu"));

		final List<ConfigurationEntity> availableConfigurations = getAvailableConfigurations();
		final Map<String, String> availableConfigurationsMap = mapAvailableConfigurations(availableConfigurations);
		addReservedValuesToMap(availableConfigurationsMap, menu);

		final ConfigurationEntity notificationTextConfiguration =
			_configurationService.findById(REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY)
				.orElseThrow(() -> new FoodBotException("Unable to find the notification text configuration"));
		final String notificationTextConfigurationValue = notificationTextConfiguration.getValue();

		return StringSubstitutor.replace(notificationTextConfigurationValue, availableConfigurationsMap);
	}

	private List<ConfigurationEntity> getAvailableConfigurations()
	{
		return _configurationService.findAll().stream()
			.filter(configuration -> !REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY.equalsIgnoreCase(configuration.getKey()))
			.collect(Collectors.toList());
	}

	private Map<String, String> mapAvailableConfigurations(final List<ConfigurationEntity> configurationEntities)
	{
		return configurationEntities.stream()
			.collect(Collectors.toMap(ConfigurationEntity::getKey, ConfigurationEntity::getValue));
	}

	private void addReservedValuesToMap(final Map<String, String> configurations, final MenuEntity menu)
	{
		final PlaceholderEntity greetingPlaceholder = _defaultPlaceholderConfiguration.getGreeting();
		final GreetingEntity randomGreeting = _greetingService.random();
		configurations.put(greetingPlaceholder.getKey(), randomGreeting.getGreeting());

		final PlaceholderEntity mainCoursePlaceholder = _defaultPlaceholderConfiguration.getMainCourse();
		configurations.put(mainCoursePlaceholder.getKey(), menu.getMainCourse());

		final PlaceholderEntity saladPlaceholder = _defaultPlaceholderConfiguration.getSalad();
		configurations.put(saladPlaceholder.getKey(), menu.getSalad());
	}
}
