package rs.laxsrbija.foodbot.persistence.bootstrap;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.persistence.model.ConfigurationKeys;
import rs.laxsrbija.foodbot.persistence.model.entity.ConfigurationEntry;
import rs.laxsrbija.foodbot.persistence.service.ConfigurationService;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements ApplicationRunner
{
	private final ConfigurationService _configurationService;

	@Override
	public void run(ApplicationArguments args)
	{
		final List<ConfigurationEntry> configurationEntries = new ArrayList<>();

		final ConfigurationEntry defaultDailyReminderHour = new ConfigurationEntry(ConfigurationKeys.DAILY_REMINDER_HOUR, "9");
		configurationEntries.add(defaultDailyReminderHour);

		final ConfigurationEntry defaultPlaceholderMessage =
			new ConfigurationEntry(ConfigurationKeys.DAILY_REMINDER_MESSAGE, "Dobro jutro (sun)\n\n"
				+ "Današnji meni:\n"
				+ "- Jelo: ${MAIN_COURSE}\n"
				+ "- Salata: ${SALAD}\n\n"
				+ "Upišite se do 10h: http://bit.ly/niri-jelo");
		configurationEntries.add(defaultPlaceholderMessage);

		saveConfigurationIfNotSet(configurationEntries);
	}

	private void saveConfigurationIfNotSet(final List<ConfigurationEntry> configurationEntries)
	{
		for (final ConfigurationEntry configurationEntry : configurationEntries)
		{
			if (!_configurationService.getConfiguration(configurationEntry.getConfigurationKey()).isPresent())
			{
				_configurationService.save(configurationEntry);
			}
		}
	}
}
