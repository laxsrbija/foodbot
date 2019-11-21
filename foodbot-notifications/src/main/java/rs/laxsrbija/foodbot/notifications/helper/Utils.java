package rs.laxsrbija.foodbot.notifications.helper;

import java.time.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.notifications.exception.FoodBotNotificationException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils
{
	public static LocalDateTime fromDate(final Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	static DayOfWeek dayOfWeekFromMenuIndexString(final String dayIndex)
	{
		final int day;
		try
		{
			day = Integer.parseInt(dayIndex);
		}
		catch (final NumberFormatException e)
		{
			throw new FoodBotNotificationException("Unable to convert '" + dayIndex + "' to an integer", e);
		}

		try
		{
			return DayOfWeek.of(day);
		}
		catch (final DateTimeException e)
		{
			throw new FoodBotNotificationException("Invalid day of the week index: " + day, e);
		}
	}
}
