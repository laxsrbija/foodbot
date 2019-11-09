package rs.laxsrbija.foodbot.messaging.service;

import java.time.LocalDateTime;
import java.util.Optional;
import rs.laxsrbija.foodbot.messaging.model.Message;

public interface MessageService
{
	public Optional<LocalDateTime> sendMessage(final Message message);
}
