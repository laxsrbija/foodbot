package rs.laxsrbija.foodbot.webapp.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.helper.DateTimeConverter;
import rs.laxsrbija.foodbot.messaging.service.MessageService;
import rs.laxsrbija.foodbot.webapp.model.Message;
import rs.laxsrbija.foodbot.webapp.model.MessageResponse;

@RestController
@RequestMapping(path = "/api/services/messaging")
@RequiredArgsConstructor
public class MessagingController
{
	private final MessageService _messageService;

	@PostMapping
	public MessageResponse sendMessage(@RequestBody final Message message)
	{
		final LocalDateTime localDateTime = _messageService.sendMessage(message.getMessage())
			.orElseThrow(() -> new FoodBotException("Unable to send a message"));

		return new MessageResponse(DateTimeConverter.fromLocalDateTime(localDateTime));
	}
}
