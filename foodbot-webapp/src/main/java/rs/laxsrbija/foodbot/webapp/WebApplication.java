package rs.laxsrbija.foodbot.webapp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.notifications.service.NotificationService;

@SpringBootApplication(scanBasePackages = {"rs.laxsrbija.foodbot"})
public class WebApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(WebApplication.class, args);
	}

	@RequiredArgsConstructor
	@Service
	public static class Strtup implements ApplicationRunner
	{
		private final NotificationService _notificationService;

		@Override
		public void run(ApplicationArguments args) throws Exception
		{
			_notificationService.checkForMenuUpdates();
		}
	}
}
