package rs.laxsrbija.foodbot.messaging.model;

import lombok.*;

@Value
@Builder
public class Emoji
{
	private String name;
	private String code;
	private String source;
}
