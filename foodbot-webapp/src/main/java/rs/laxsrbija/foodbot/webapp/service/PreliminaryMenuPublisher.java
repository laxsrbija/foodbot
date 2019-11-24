package rs.laxsrbija.foodbot.webapp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.*;
import rs.laxsrbija.foodbot.common.service.PreliminaryMenuService;
import rs.laxsrbija.foodbot.common.service.MenuService;

@Service
@RequiredArgsConstructor
public class PreliminaryMenuPublisher
{
	private final MenuService _menuService;
	private final PreliminaryMenuService _preliminaryMenuService;

	public List<MenuEntity> publishReviewMenu(final PreliminaryMenuEntity entity)
	{
		final List<ReceivedMenuItemEntity> receivedMenuItemEntities = entity.getReceivedMenuItemEntities();

		final List<MenuEntity> savedMenuEntities = receivedMenuItemEntities.stream()
			.map(PreliminaryMenuPublisher::convertToMenu)
			.map(_menuService::save)
			.collect(Collectors.toList());

		// Remove the preliminary menu instance
		_preliminaryMenuService.deleteById(entity.getId());

		return savedMenuEntities;
	}

	private static MenuEntity convertToMenu(final ReceivedMenuItemEntity entity)
	{
		return MenuEntity.builder()
			.dayOfWeek(entity.getDayOfWeek())
			.mainCourse(entity.getMainCourse())
			.salad(entity.getSalad())
			.sendReminder(true).build();
	}
}
