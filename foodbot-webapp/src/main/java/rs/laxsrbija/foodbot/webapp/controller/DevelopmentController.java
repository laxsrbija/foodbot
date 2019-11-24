package rs.laxsrbija.foodbot.webapp.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.MenuEntity;
import rs.laxsrbija.foodbot.common.service.MenuService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;
import rs.laxsrbija.foodbot.webapp.service.NotificationTextParser;

@RestController
@Profile("development")
@RequestMapping(path = "/api/services/development")
@RequiredArgsConstructor
public class DevelopmentController
{
	private final MenuService _menuService;
	private final NotificationTextParser _notificationTextParser;

	@GetMapping("notification-message")
	public String getNotificationMessage()
	{
		final MenuEntity menu = _menuService.getMenuForToday()
			.orElseThrow(() -> new ResourceNotFoundException("Menu not set for today"));
		return _notificationTextParser.parseNotificationText(menu);
	}
}
