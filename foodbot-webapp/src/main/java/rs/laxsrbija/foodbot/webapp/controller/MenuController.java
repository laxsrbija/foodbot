package rs.laxsrbija.foodbot.webapp.controller;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.MenuDto;
import rs.laxsrbija.foodbot.common.model.dto.MenuReviewDto;
import rs.laxsrbija.foodbot.common.model.entity.MenuEntity;
import rs.laxsrbija.foodbot.common.model.mapper.MenuMapper;
import rs.laxsrbija.foodbot.common.model.mapper.MenuReviewMapper;
import rs.laxsrbija.foodbot.common.service.MenuService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;
import rs.laxsrbija.foodbot.webapp.service.MenuReviewPublisher;

@RestController
@RequestMapping(path = "/api/services/menu")
@RequiredArgsConstructor
public class MenuController
{
	private final MenuService _menuService;
	private final MenuReviewPublisher _menuReviewPublisher;

	@GetMapping
	public List<MenuDto> getWeeklyMenu()
	{
		return _menuService.findAll().stream()
			.map(MenuMapper::toDto)
			.collect(Collectors.toList());
	}

	@PostMapping
	public MenuDto saveMenu(@RequestBody final MenuDto newMenu)
	{
		final MenuEntity savedMenu = _menuService.save(MenuMapper.fromDto(newMenu));
		return MenuMapper.toDto(savedMenu);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteWeeklyGreetings()
	{
		_menuService.deleteAll();
	}

	@GetMapping("/{id}")
	public MenuDto getById(@PathVariable("id") final Integer id)
	{
		final DayOfWeek dayOfWeek = DayOfWeek.of(id);
		final MenuEntity menuEntity = _menuService.findById(dayOfWeek)
			.orElseThrow(() -> new ResourceNotFoundException("Unable to find a menu with ID " + dayOfWeek.name()));
		return MenuMapper.toDto(menuEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGreetingById(@PathVariable("id") final Integer id)
	{
		final DayOfWeek dayOfWeek = DayOfWeek.of(id);
		_menuService.deleteById(dayOfWeek);
	}

	@GetMapping("/today")
	public MenuDto getMenuForToday()
	{
		final MenuEntity menuForToday = _menuService.getMenuForToday()
			.orElseThrow(() -> new ResourceNotFoundException("Unable to get today's menu"));
		return MenuMapper.toDto(menuForToday);
	}

	@GetMapping("/publish")
	public List<MenuDto> publishMenu(@RequestBody final MenuReviewDto dto)
	{
		return _menuReviewPublisher.publishReviewMenu(MenuReviewMapper.fromDto(dto)).stream()
			.map(MenuMapper::toDto)
			.collect(Collectors.toList());
	}
}
