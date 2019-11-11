package rs.laxsrbija.foodbot.notifications.exception;

public class FoodBotNotificationException extends RuntimeException
{
	public FoodBotNotificationException(String s)
	{
		super(s);
	}

	public FoodBotNotificationException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	public FoodBotNotificationException(Throwable throwable)
	{
		super(throwable);
	}
}
