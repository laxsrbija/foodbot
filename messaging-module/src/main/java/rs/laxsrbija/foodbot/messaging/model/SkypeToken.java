package rs.laxsrbija.foodbot.messaging.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SkypeToken
{
	final String token;
	final LocalDateTime expiry;
}
