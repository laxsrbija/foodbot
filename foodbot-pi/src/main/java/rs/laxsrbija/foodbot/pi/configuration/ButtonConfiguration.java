package rs.laxsrbija.foodbot.pi.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import com.pi4j.io.gpio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.pi.listener.ButtonListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ButtonConfiguration implements ApplicationRunner
{
	private final GpioController _gpio;
	private final ButtonListener _buttonListener;
	private final PiServiceConfiguration _configuration;

	@Override
	public void run(final ApplicationArguments args)
	{
		final Integer gpioPin = _configuration.getButton().getGpioPin();
		final Pin buttonPin = RaspiPin.getPinByAddress(gpioPin);

		final GpioPinDigitalInput button = _gpio.provisionDigitalInputPin(buttonPin, PinPullResistance.PULL_DOWN);
		button.setShutdownOptions(true);
		button.addListener(_buttonListener);
	}
}
