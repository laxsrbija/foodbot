package rs.laxsrbija.foodbot.notifications.configuration;

import java.util.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "foodbot.notifications")
public class NotificationServiceConfiguration
{
	private String expectedSubject = "";
	private Set<String> whitelistedSenders = new HashSet<>();
	private Pop3Configuration pop3 = new Pop3Configuration();

	@Getter
	@Setter
	public static class Pop3Configuration
	{
		private String host;
		private Integer port;
		private String username;
		private String password;
	}
}
