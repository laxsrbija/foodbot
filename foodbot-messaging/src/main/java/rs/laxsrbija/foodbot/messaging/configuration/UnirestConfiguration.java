package rs.laxsrbija.foodbot.messaging.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import kong.unirest.Unirest;

@Configuration
public class UnirestConfiguration implements ApplicationRunner
{
	@Override
	public void run(ApplicationArguments args)
	{
		Unirest.config().enableCookieManagement(false);
	}
}
