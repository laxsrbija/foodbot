package rs.laxsrbija.foodbot.messaging.exception;

public class FoodBotMessagingException extends RuntimeException
{
	public FoodBotMessagingException(String s)
	{
		super(s);
	}

	public FoodBotMessagingException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	public FoodBotMessagingException(Throwable throwable)
	{
		super(throwable);
	}
}
