package rs.laxsrbija.foodbot.webapp.controller;

import java.time.DayOfWeek;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.persistence.model.entity.DailyMenu;
import rs.laxsrbija.foodbot.persistence.service.DailyMenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/services/daily-menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DailyMenuController
{
	private final DailyMenuService _dailyMenuService;

	@GetMapping
	public List<DailyMenu> getWeeklyMenu()
	{
		return _dailyMenuService.getWeeklyMenu();
	}

	@GetMapping("/{day}")
	public DailyMenu getDailyMenu(@PathVariable("day") final DayOfWeek dayOfWeek)
	{
		return _dailyMenuService.getDailyMenu(dayOfWeek).orElse(null);
	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public DailyMenu saveDailyMenu(@RequestBody final DailyMenu dailyMenu) // TODO Replace with a DTO
	{
		return _dailyMenuService.save(dailyMenu);
	}

	@DeleteMapping
	public void deleteWeeklyMenu()
	{
		_dailyMenuService.deleteWeeklyMenu();
	}
}
