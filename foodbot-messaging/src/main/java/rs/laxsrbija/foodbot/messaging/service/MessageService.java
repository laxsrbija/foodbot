package rs.laxsrbija.foodbot.messaging.service;

import java.time.LocalDateTime;
import java.util.Optional;
import rs.laxsrbija.foodbot.messaging.model.Message;

public interface MessageService
{
	/**
	 * Sends message to a desired group, as defined in the provided {@link Message}.
	 *
	 * @return An {@link Optional} containing a {@link LocalDateTime} object with the message server time
	 * @throws rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException when an error occurs
	 */
	Optional<LocalDateTime> sendMessage(final Message message);
}
