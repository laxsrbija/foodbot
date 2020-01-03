package rs.laxsrbija.foodbot.pi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.*;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "foodbot.pi")
public class PiServiceConfiguration
{
	private BuzzerConfiguration buzzer;
	private ButtonConfiguration button;

	@Data
	@NoArgsConstructor
	public static class BuzzerConfiguration
	{
		private boolean enabled;
		private Integer gpioPin;
	}

	@Data
	@NoArgsConstructor
	public static class ButtonConfiguration
	{
		private Integer gpioPin;
	}
}
