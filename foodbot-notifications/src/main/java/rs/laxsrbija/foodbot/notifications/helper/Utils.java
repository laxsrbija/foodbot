package rs.laxsrbija.foodbot.notifications.helper;

import java.time.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils
{
	public static LocalDateTime fromDate(final Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static DayOfWeek dayOfWeekFromMenuIndexString(final String dayIndex)
	{
		final int day = Integer.parseInt(dayIndex);
		return DayOfWeek.of(day);
	}
}
