package rs.laxsrbija.foodbot.messaging.model;

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
}
