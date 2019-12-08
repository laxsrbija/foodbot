package rs.laxsrbija.foodbot.pi.listener;

import org.springframework.stereotype.Service;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.pi.service.ButtonService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButtonListener implements GpioPinListenerDigital
{
	private final ButtonService _buttonService;

	@Override
	public void handleGpioPinDigitalStateChangeEvent(final GpioPinDigitalStateChangeEvent event)
	{
		final PinState state = event.getState();

		// We only want to perform action when the button is released
		if (state.isLow())
		{
			log.info("The button has been released");
			_buttonService.buttonAction();
		}
	}
}
