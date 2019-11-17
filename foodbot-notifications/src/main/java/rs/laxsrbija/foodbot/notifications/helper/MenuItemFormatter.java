package rs.laxsrbija.foodbot.notifications.helper;

import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.exception.FoodBotNotificationException;
import rs.laxsrbija.foodbot.notifications.model.ParsedMenuItem;
import rs.laxsrbija.foodbot.notifications.model.ReceivedMenuItem;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuItemFormatter
{
	public static ReceivedMenuItem formatMenuItem(final ParsedMenuItem rawMenuItem)
	{
		final String day = rawMenuItem.getDay();
		final String course = rawMenuItem.getCourse();
		final String salad = rawMenuItem.getSalad();

		final ReceivedMenuItem.ReceivedMenuItemBuilder builder = ReceivedMenuItem.builder();

		if (day != null)
		{
			try
			{
				final DayOfWeek dayOfWeek = Utils.dayOfWeekFromMenuIndexString(day.trim());
				builder.dayOfWeek(dayOfWeek);
			}
			catch (final FoodBotNotificationException e)
			{
				log.error(e.getMessage());
			}
		}

		if (course != null)
		{
			builder.mainCourse(course.trim());
		}

		if (salad != null)
		{
			builder.salad(salad.trim());
		}

		return builder.build();
	}
}
