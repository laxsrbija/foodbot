package rs.laxsrbija.foodbot.persistence.service;

import java.time.DayOfWeek;
import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.persistence.model.entity.DailyMenu;
import rs.laxsrbija.foodbot.persistence.repository.DailyMenuRepository;

@Service
@RequiredArgsConstructor
public class DailyMenuService
{
	private final DailyMenuRepository _dailyMenuRepository;

	public DailyMenu save(final DailyMenu dailyMenu)
	{
		return _dailyMenuRepository.save(dailyMenu);
	}

	public Optional<DailyMenu> getDailyMenu(final DayOfWeek dayOfWeek)
	{
		return _dailyMenuRepository.findById(dayOfWeek);
	}

	public List<DailyMenu> getWeeklyMenu()
	{
		final Iterable<DailyMenu> dailyMenuIterable = _dailyMenuRepository.findAll();
		final List<DailyMenu> dailyMenuList = new ArrayList<>();

		for (final DailyMenu dailyMenu : dailyMenuIterable)
		{
			dailyMenuList.add(dailyMenu);
		}

		Collections.sort(dailyMenuList);

		return dailyMenuList;
	}

	public void deleteWeeklyMenu()
	{
		_dailyMenuRepository.deleteAll();
	}
}
