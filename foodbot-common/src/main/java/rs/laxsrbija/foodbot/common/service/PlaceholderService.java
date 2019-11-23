package rs.laxsrbija.foodbot.common.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.configuration.DefaultPlaceholderConfiguration;
import rs.laxsrbija.foodbot.common.model.entity.PlaceholderEntity;

@Service
@RequiredArgsConstructor
public class PlaceholderService
{
	final ConfigurationService _configurationService;
	final DefaultPlaceholderConfiguration _defaultPlaceholderConfiguration;

	public List<PlaceholderEntity> findAll()
	{
		final List<PlaceholderEntity> placeholders = new ArrayList<>();

		// Manually add the default placeholder information
		placeholders.add(_defaultPlaceholderConfiguration.getMainCourse());
		placeholders.add(_defaultPlaceholderConfiguration.getSalad());
		placeholders.add(_defaultPlaceholderConfiguration.getGreeting());

		// Add user-generated placeholders
		placeholders.addAll(_configurationService.findAll());

		return placeholders;
	}
}
