package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.ConfigurationDto;
import rs.laxsrbija.foodbot.common.model.entity.ConfigurationEntity;
import rs.laxsrbija.foodbot.common.model.mapper.ConfigurationMapper;
import rs.laxsrbija.foodbot.common.service.ConfigurationService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/api/services/configuration")
@RequiredArgsConstructor
public class ConfigurationController
{
	private final ConfigurationService _configurationService;

	@GetMapping
	public List<ConfigurationDto> getAllConfigurationEntries()
	{
		return _configurationService.findAll().stream()
			.map(ConfigurationMapper::toDto)
			.collect(Collectors.toList());
	}

	@PostMapping
	public ConfigurationDto saveConfiguration(@RequestBody final ConfigurationDto configurationDto)
	{
		final ConfigurationEntity entity = _configurationService.save(ConfigurationMapper.fromDto(configurationDto));
		return ConfigurationMapper.toDto(entity);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllConfigurationEntries()
	{
		_configurationService.deleteAll();
	}

	@GetMapping("/{id}")
	public ConfigurationDto getById(@PathVariable("id") final String id)
	{
		final ConfigurationEntity configurationEntity = _configurationService.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Unable to find a configuration with ID " + id));
		return ConfigurationMapper.toDto(configurationEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGreetingById(@PathVariable("id") final String id)
	{
		_configurationService.deleteById(id);
	}
}
