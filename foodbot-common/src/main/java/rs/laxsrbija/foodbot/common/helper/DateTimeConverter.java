package rs.laxsrbija.foodbot.common.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeConverter
{
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	public static String fromLocalDateTime(final LocalDateTime localDateTime)
	{
		return FORMATTER.format(localDateTime);
	}

	public static LocalDateTime toLocalDateTime(final String dateString)
	{
		return LocalDateTime.parse(dateString, FORMATTER);
	}
}
