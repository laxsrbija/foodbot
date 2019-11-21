package rs.laxsrbija.foodbot.notifications.helper;

import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.entity.ReceivedMenuItem;
import rs.laxsrbija.foodbot.notifications.exception.FoodBotNotificationException;
import rs.laxsrbija.foodbot.notifications.model.ParsedMenuItem;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuItemFormatter
{
	public static ReceivedMenuItem formatMenuItem(final ParsedMenuItem rawMenuItem)
	{
		final String day = rawMenuItem.getDay();
		final String course = rawMenuItem.getCourse();
		final String salad = rawMenuItem.getSalad();

		final ReceivedMenuItem receivedMenuItem = new ReceivedMenuItem();

		if (day != null)
		{
			try
			{
				final DayOfWeek dayOfWeek = Utils.dayOfWeekFromMenuIndexString(day.trim());
				receivedMenuItem.setDayOfWeek(dayOfWeek);
			}
			catch (final FoodBotNotificationException e)
			{
				log.error(e.getMessage());
			}
		}

		if (course != null && !course.trim().isEmpty())
		{
			receivedMenuItem.setMainCourse(course.trim());
		}

		if (salad != null && !salad.trim().isEmpty())
		{
			receivedMenuItem.setSalad(salad.trim());
		}

		return receivedMenuItem;
	}
}
