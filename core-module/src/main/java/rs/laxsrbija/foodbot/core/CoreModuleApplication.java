package rs.laxsrbija.foodbot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"rs.laxsrbija.foodbot"})
public class CoreModuleApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(CoreModuleApplication.class, args);
	}
}
