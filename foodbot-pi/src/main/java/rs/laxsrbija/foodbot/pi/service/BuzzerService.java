package rs.laxsrbija.foodbot.pi.service;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.pi4j.io.gpio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.pi.configuration.PiServiceConfiguration;
import rs.laxsrbija.foodbot.pi.model.BuzzType;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuzzerService
{
	private static final long BUZZER_SUCCESS_INTERVAL = 200L;
	private static final long BUZZER_SUCCESS_PAUSE = 100L;
	private static final long BUZZER_ERROR_INTERVAL = 500L;

	private final GpioController _gpio;
	private final PiServiceConfiguration _configuration;

	private GpioPinDigitalOutput buzzer;

	@PostConstruct
	private void init()
	{
		final Integer gpioPin = _configuration.getBuzzer().getGpioPin();
		final Pin buzzerPin = RaspiPin.getPinByAddress(gpioPin);

		buzzer = _gpio.provisionDigitalOutputPin(buzzerPin, PinState.LOW);
		buzzer.setShutdownOptions(true, PinState.LOW);
	}

	public void buzz(final BuzzType buzzType)
	{
		final PiServiceConfiguration.BuzzerConfiguration buzzerConfiguration = _configuration.getBuzzer();
		if (!buzzerConfiguration.isEnabled())
		{
			log.info("The buzzer is disabled");
			return;
		}

		if (buzzType == BuzzType.SUCCESS)
		{
			buzzSuccess();
		}
		else if (buzzType == BuzzType.ERROR)
		{
			buzzError();
		}
	}

	private void buzzSuccess()
	{
		try
		{
			// This creates a <BEEP><PAUSE><BEEP> pattern
			buzzer.high();
			Thread.sleep(BUZZER_SUCCESS_INTERVAL);

			buzzer.low();
			Thread.sleep(BUZZER_SUCCESS_PAUSE);

			buzzer.high();
			Thread.sleep(BUZZER_SUCCESS_INTERVAL);

			buzzer.low();
		}
		catch (final InterruptedException e)
		{
			log.error(e.getMessage());
		}
	}

	private void buzzError()
	{
		try
		{
			// This creates a <BEEP><PAUSE><BEEP> pattern
			buzzer.high();
			Thread.sleep(BUZZER_ERROR_INTERVAL);

			buzzer.low();
		}
		catch (final InterruptedException e)
		{
			log.error(e.getMessage());
		}
	}
}
