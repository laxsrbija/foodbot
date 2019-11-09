package rs.laxsrbija.foodbot.messaging.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SkypeToken extends AbstractToken
{
	public SkypeToken(final String token, final LocalDateTime expirationDate)
	{
		super(token, expirationDate);
	}
}
