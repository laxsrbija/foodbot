package rs.laxsrbija.foodbot.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"rs.laxsrbija.foodbot"})
public class WebApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(WebApplication.class, args);
	}
}
