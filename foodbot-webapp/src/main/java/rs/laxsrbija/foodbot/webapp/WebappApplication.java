package rs.laxsrbija.foodbot.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"rs.laxsrbija.foodbot"})
public class WebappApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(WebappApplication.class, args);
	}
}
