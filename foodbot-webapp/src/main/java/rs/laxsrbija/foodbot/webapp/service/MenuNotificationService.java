package rs.laxsrbija.foodbot.webapp.service;

import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.model.entity.MenuEntity;
import rs.laxsrbija.foodbot.common.service.MenuService;
import rs.laxsrbija.foodbot.messaging.service.MessageService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuNotificationService
{
	private final MenuService _menuService;
	private final MessageService _messageService;
	private final NotificationTextParser _notificationTextParser;

	@Scheduled(cron = "${foodbot.scheduling.menu-notification-schedule}")
	public void sendMenuNotification()
	{
		final Optional<MenuEntity> menuForToday = _menuService.getMenuForToday();
		if (menuForToday.isPresent())
		{
			final MenuEntity menu = menuForToday.get();
			final String parsedNotificationText = _notificationTextParser.parseNotificationText(menu);

			log.info("Sending menu notification...");
			_messageService.sendMessage(parsedNotificationText);
		}
		else
		{
			log.info("Menu not set for today, skipping the notification process");
		}
	}
}
