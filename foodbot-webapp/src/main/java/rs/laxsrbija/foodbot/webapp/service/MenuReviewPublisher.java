package rs.laxsrbija.foodbot.webapp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.*;
import rs.laxsrbija.foodbot.common.service.MenuReviewService;
import rs.laxsrbija.foodbot.common.service.MenuService;

@Service
@RequiredArgsConstructor
public class MenuReviewPublisher
{
	private final MenuService _menuService;
	private final MenuReviewService _menuReviewService;

	public List<MenuEntity> publishReviewMenu(final MenuReviewEntity entity)
	{
		final List<ReceivedMenuItemEntity> receivedMenuItemEntities = entity.getReceivedMenuItemEntities();

		final List<MenuEntity> savedMenuEntities = receivedMenuItemEntities.stream()
			.map(MenuReviewPublisher::convertToMenu)
			.map(_menuService::save)
			.collect(Collectors.toList());

		// Remove the preliminary menu instance
		_menuReviewService.deleteById(entity.getId());

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
