package rs.laxsrbija.foodbot.pi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

@Configuration
public class GpioControllerConfiguration
{
	@Bean
	public GpioController getGpioController()
	{
		return GpioFactory.getInstance();
	}
}
