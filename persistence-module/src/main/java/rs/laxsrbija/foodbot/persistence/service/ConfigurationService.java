package rs.laxsrbija.foodbot.persistence.service;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.persistence.model.ConfigurationKeys;
import rs.laxsrbija.foodbot.persistence.model.entity.ConfigurationEntry;
import rs.laxsrbija.foodbot.persistence.repository.ConfigurationRepository;

@Service
@RequiredArgsConstructor
public class ConfigurationService
{
	private static final ConfigurationKeys REMINDER_MESSAGE_KEY = ConfigurationKeys.DAILY_REMINDER_MESSAGE;
	
	private final ConfigurationRepository _configurationRepository;

	public ConfigurationEntry save(final ConfigurationEntry configurationEntry)
	{
		return _configurationRepository.save(configurationEntry);
	}

	public Optional<ConfigurationEntry> getConfiguration(final ConfigurationKeys configurationKey)
	{
		return _configurationRepository.findById(configurationKey);
	}

	public Set<ConfigurationEntry> getConfigurations()
	{
		final Iterable<ConfigurationEntry> dailyMenuIterable = _configurationRepository.findAll();
		final Set<ConfigurationEntry> configurationSet = new HashSet<>();

		for (final ConfigurationEntry configurationEntry : dailyMenuIterable)
		{
			configurationSet.add(configurationEntry);
		}

		return configurationSet;
	}
	
	public Set<ConfigurationKeys> getAllPlaceholders()
	{
		final EnumSet<ConfigurationKeys> configurationKeys = EnumSet.allOf(ConfigurationKeys.class);
		configurationKeys.remove(REMINDER_MESSAGE_KEY);
		return configurationKeys;
	}
}
