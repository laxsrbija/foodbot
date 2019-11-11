package rs.laxsrbija.foodbot.messaging.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "foodbot.messaging")
public class MessagingServiceConfiguration
{
	private String username;
	private String password;

	// Use a safer and shorter token expiry time than the one returned by API
	private boolean shorterTokenLifespan;
}
