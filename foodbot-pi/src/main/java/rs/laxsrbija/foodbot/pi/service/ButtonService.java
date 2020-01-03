package rs.laxsrbija.foodbot.pi.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.model.entity.ArrivalEntity;
import rs.laxsrbija.foodbot.common.service.ArrivalService;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;
import rs.laxsrbija.foodbot.messaging.service.MessageService;
import rs.laxsrbija.foodbot.pi.model.BuzzType;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButtonService
{
	private final BuzzerService _buzzerService;
	private final ArrivalService _arrivalService;
	private final MessageService _messageService;

	public void buttonAction()
	{
		try
		{
			sendFoodArrivalMessage();
			_buzzerService.buzz(BuzzType.SUCCESS);
		}
		catch (final Exception e)
		{
			log.error("Unable to send the food arrival notification: " + e.getMessage());
			_buzzerService.buzz(BuzzType.ERROR);
		}
	}

	private void sendFoodArrivalMessage()
	{
		final ArrivalEntity randomFoodArrivalMessage = _arrivalService.random();
		final String arrivalMessage = randomFoodArrivalMessage.getArrivalMessage();

		final Optional<LocalDateTime> dateTimeReceived = _messageService.sendMessage(arrivalMessage);

		if (!dateTimeReceived.isPresent())
		{
			throw new FoodBotMessagingException("Time of message reception is not present");
		}

		final LocalDateTime localDateTime = dateTimeReceived.get();
		log.info("Food arrival message received at " + localDateTime);
	}
}
