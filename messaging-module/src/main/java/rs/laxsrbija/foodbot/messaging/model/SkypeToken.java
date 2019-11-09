package rs.laxsrbija.foodbot.messaging.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SkypeToken
{
	final String token;
	final String expiry;
}
