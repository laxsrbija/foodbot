package rs.laxsrbija.foodbot.notifications.configuration;

import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "foodbot.notifications")
public class NotificationServiceConfiguration
{
	private String expectedSubject;
	private Set<String> whitelistedSenders;
	private Pop3Configuration pop3;
	private SmtpConfiguration smtp;
	private CsvConfiguration csv;

	@Data
	@NoArgsConstructor
	public static class Pop3Configuration
	{
		private String host;
		private Integer port;
		private String username;
		private String password;
	}

	@Data
	@NoArgsConstructor
	public static class SmtpConfiguration
	{
		private String host;
		private Integer port;
		private String username;
		private String password;
		private String displayName;
	}

	@Data
	@NoArgsConstructor
	public static class CsvConfiguration
	{
		private char separator;
	}
}
