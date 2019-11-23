package rs.laxsrbija.foodbot.common.exception;

public class FoodBotException extends RuntimeException
{
	public FoodBotException(String s)
	{
		super(s);
	}

	public FoodBotException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	public FoodBotException(Throwable throwable)
	{
		super(throwable);
	}
}
