package rs.laxsrbija.foodbot.common.bootstrap;

import static rs.laxsrbija.foodbot.common.service.ConfigurationService.REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.model.entity.ConfigurationEntity;
import rs.laxsrbija.foodbot.common.repository.ConfigurationRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConfigurationDataLoader implements ApplicationRunner
{
	private static final String DEFAULT_REMINDER_NOTIFICATION_TEXT_CONFIGURATION_VALUE = "This is the default reminder message";

	private final ConfigurationRepository _configurationRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		loadDefaultNotificationTextConfiguration();
	}

	private void loadDefaultNotificationTextConfiguration()
	{
		if (!_configurationRepository.findById(REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY).isPresent())
		{
			log.info("Default notification text configuration is not present, creating a sample one");

			final ConfigurationEntity configurationEntity = new ConfigurationEntity();
			configurationEntity.setKey(REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY);
			configurationEntity.setValue(DEFAULT_REMINDER_NOTIFICATION_TEXT_CONFIGURATION_VALUE);

			_configurationRepository.save(configurationEntity);
		}
	}
}
