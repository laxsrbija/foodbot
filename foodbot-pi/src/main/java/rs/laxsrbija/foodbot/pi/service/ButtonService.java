package rs.laxsrbija.foodbot.pi.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.pi.model.BuzzType;

@Service
@RequiredArgsConstructor
public class ButtonService
{
	private final BuzzerService _buzzerService;

	public void buttonAction()
	{
		// TODO: Once the food arrival logic is in place, it should be interacted with from here
		_buzzerService.buzz(BuzzType.SUCCESS);
	}
}
