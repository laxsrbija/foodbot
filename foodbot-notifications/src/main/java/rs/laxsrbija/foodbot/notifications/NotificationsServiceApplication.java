package rs.laxsrbija.foodbot.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = {"rs.laxsrbija.foodbot"})
public class NotificationsServiceApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(NotificationsServiceApplication.class, args);
	}
}
