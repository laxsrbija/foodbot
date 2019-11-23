package rs.laxsrbija.foodbot.common.helper;

import java.time.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayOfWeekUtils
{
	public static LocalDateTime fromDate(final Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static DayOfWeek dayOfWeekFromIndexString(final String dayIndex)
	{
		final int day;
		try
		{
			day = Integer.parseInt(dayIndex);
		}
		catch (final NumberFormatException e)
		{
			throw new FoodBotException("Unable to convert '" + dayIndex + "' to an integer", e);
		}

		return dayOfWeekFromIndex(day);
	}

	public static DayOfWeek dayOfWeekFromIndex(final int dayIndex)
	{
		try
		{
			return DayOfWeek.of(dayIndex);
		}
		catch (final DateTimeException e)
		{
			throw new FoodBotException("Invalid day of the week index: " + dayIndex, e);
		}
	}
}
