package rs.laxsrbija.foodbot.notifications.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InboundMenuEmail
{
	private String sender;
	private LocalDateTime dateSent;
	private LocalDateTime dateReceived;
	private String message;
}
