package rs.laxsrbija.foodbot.webapp.controller;

import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.persistence.model.entity.ConfigurationEntry;
import rs.laxsrbija.foodbot.persistence.service.ConfigurationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/services/configuration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConfigurationController
{
	private final ConfigurationService _configurationService;

	@GetMapping("")
	public Set<ConfigurationEntry> getAllConfigurationEntries()
	{
		return _configurationService.getConfigurations();
	}

	@PostMapping("")
	public ConfigurationEntry saveConfigurationEntry(@RequestBody final ConfigurationEntry configurationEntry)
	{
		return _configurationService.save(configurationEntry);
	}
}
