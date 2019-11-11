package rs.laxsrbija.foodbot.notifications;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.email.InboundEmailService;

@Slf4j
@SpringBootApplication
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
		private final InboundEmailService _inboundEmailService;

		@Override
		public void run(ApplicationArguments args) throws Exception
		{
			log.info("START: {}", (String)null);
			_inboundEmailService.getEmailFromServer();
		}
	}
}
