package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.PlaceholderDto;
import rs.laxsrbija.foodbot.common.model.mapper.PlaceholderMapper;
import rs.laxsrbija.foodbot.common.service.PlaceholderService;

@RestController
@RequestMapping(path = "/api/services/placeholder")
@RequiredArgsConstructor
public class PlaceholderController
{
	private final PlaceholderService _placeholderService;

	@GetMapping
	public List<PlaceholderDto> getAllConfigurationEntries()
	{
		return _placeholderService.findAll().stream()
			.map(PlaceholderMapper::toDto)
			.collect(Collectors.toList());
	}
}
