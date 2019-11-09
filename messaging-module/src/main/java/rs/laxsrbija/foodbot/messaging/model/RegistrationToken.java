package rs.laxsrbija.foodbot.messaging.model;

import java.time.LocalDateTime;
import lombok.*;

@Setter
@Getter
@ToString(callSuper = true)
public class RegistrationToken extends AbstractToken
{
	private String messageHost;
	private String endpoint;

	public RegistrationToken(final String messageHost)
	{
		super(null, null);
		this.endpoint = null;
		this.messageHost = messageHost;
	}

	public RegistrationToken(
		final String token,
		final LocalDateTime expirationDate,
		final String messageHost,
		final String endpoint)
	{
		super(token, expirationDate);
		this.messageHost = messageHost;
		this.endpoint = endpoint;
	}
}
