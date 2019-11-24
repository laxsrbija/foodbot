package rs.laxsrbija.foodbot.webapp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.*;
import rs.laxsrbija.foodbot.common.service.MenuService;

@Service
@RequiredArgsConstructor
public class MenuReviewPublisher
{
	private final MenuService _menuService;

	public List<MenuEntity> publishReviewMenu(final MenuReviewEntity entity)
	{
		final List<ReceivedMenuItemEntity> receivedMenuItemEntities = entity.getReceivedMenuItemEntities();
		return receivedMenuItemEntities.stream()
			.map(MenuReviewPublisher::convertToMenu)
			.map(_menuService::save)
			.collect(Collectors.toList());
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
