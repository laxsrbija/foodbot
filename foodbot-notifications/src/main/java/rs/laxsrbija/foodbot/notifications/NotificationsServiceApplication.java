package rs.laxsrbija.foodbot.notifications;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.entity.ReceivedMenuItem;
import rs.laxsrbija.foodbot.common.entity.WeeklyMenuNotification;
import rs.laxsrbija.foodbot.common.repository.WeeklyMenuNotificationRepository;
import rs.laxsrbija.foodbot.notifications.service.NotificationService;

@Slf4j
@SpringBootApplication(scanBasePackages = {"rs.laxsrbija.foodbot"})
public class NotificationsServiceApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(NotificationsServiceApplication.class, args);
	}

	@Service
	@RequiredArgsConstructor
	public static class Startup implements ApplicationRunner
	{
		private final NotificationService _notificationService;
		private final WeeklyMenuNotificationRepository _notificationRepository;

		@Override
		public void run(ApplicationArguments args) throws Exception
		{
			//_notificationService.checkForMenuUpdates();
			final ReceivedMenuItem receivedMenuItem = new ReceivedMenuItem();
			receivedMenuItem.setDayOfWeek(DayOfWeek.FRIDAY);
			receivedMenuItem.setMainCourse("MC");
			receivedMenuItem.setSalad("SD");

			WeeklyMenuNotification weeklyMenuNotification = new WeeklyMenuNotification();
			weeklyMenuNotification.setDateReceived(LocalDateTime.now());
			weeklyMenuNotification.setDateSent(LocalDateTime.now());
			weeklyMenuNotification.setRawText("ASD");
			weeklyMenuNotification.setSender("SENDER");
			weeklyMenuNotification.setReceivedMenuItems(Collections.singletonList(receivedMenuItem));
			weeklyMenuNotification.setId(1L);

			_notificationRepository.save(weeklyMenuNotification);

			final Optional<WeeklyMenuNotification> byId = _notificationRepository.findById(1L);
			log.info(byId.get().toString());
		}
	}
}
