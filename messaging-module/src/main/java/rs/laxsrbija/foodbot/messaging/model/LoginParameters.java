package rs.laxsrbija.foodbot.messaging.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginParameters
{
	private final String ppftField;
	private final String msprequCookie;
	private final String mspokCookie;
}
