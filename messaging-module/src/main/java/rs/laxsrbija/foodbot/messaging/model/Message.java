package rs.laxsrbija.foodbot.messaging.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Message
{
	private final String messageText;
	private final String groupId;
}
