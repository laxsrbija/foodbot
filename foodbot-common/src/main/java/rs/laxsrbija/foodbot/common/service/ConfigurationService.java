package rs.laxsrbija.foodbot.common.service;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.model.entity.ConfigurationEntity;
import rs.laxsrbija.foodbot.common.repository.ConfigurationRepository;
import rs.laxsrbija.foodbot.common.validator.ConfigurationValidator;

@Service
@RequiredArgsConstructor
public class ConfigurationService implements EntityServiceInterface<ConfigurationEntity, String>
{
	public static final String REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY = "NOTIFICATION_TEXT";

	private final ConfigurationRepository _configurationRepository;
	private final ConfigurationValidator _configurationValidator;

	@Override
	public ConfigurationEntity save(final ConfigurationEntity entity)
	{
		_configurationValidator.validateConfiguration(entity);
		return _configurationRepository.save(transformConfigurationKey(entity));
	}

	@Override
	public List<ConfigurationEntity> findAll()
	{
		final List<ConfigurationEntity> configurationEntities = new ArrayList<>();

		final Iterable<ConfigurationEntity> configurationEntityIterable = _configurationRepository.findAll();
		configurationEntityIterable.forEach(configurationEntities::add);

		return configurationEntities;
	}

	@Override
	public Optional<ConfigurationEntity> findById(final String id)
	{
		return _configurationRepository.findById(id);
	}

	@Override
	public void deleteAll()
	{
		_configurationRepository.deleteAllByKeyIsNot(REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY);
	}

	@Override
	public void deleteById(final String id)
	{
		if (id.equalsIgnoreCase(REMINDER_NOTIFICATION_TEXT_CONFIGURATION_KEY))
		{
			throw new FoodBotException("The menu notification configuration cannot be deleted");
		}

		_configurationRepository.deleteById(id);
	}

	private static ConfigurationEntity transformConfigurationKey(final ConfigurationEntity entity)
	{
		final String newKey = entity.getKey().toUpperCase();
		entity.setKey(newKey);

		return entity;
	}
}
