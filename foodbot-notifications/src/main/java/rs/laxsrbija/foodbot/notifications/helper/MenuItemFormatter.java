package rs.laxsrbija.foodbot.notifications.helper;

import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.notifications.model.ParsedMenuItem;
import rs.laxsrbija.foodbot.notifications.model.ReceivedMenuItem;

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
			final DayOfWeek dayOfWeek = Utils.dayOfWeekFromMenuIndexString(day.trim());
			builder.dayOfWeek(dayOfWeek);
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
