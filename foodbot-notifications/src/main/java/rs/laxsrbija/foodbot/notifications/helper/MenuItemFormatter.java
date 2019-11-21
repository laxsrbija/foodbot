package rs.laxsrbija.foodbot.notifications.helper;

import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.model.entity.ReceivedMenuItemEntity;
import rs.laxsrbija.foodbot.notifications.exception.FoodBotNotificationException;
import rs.laxsrbija.foodbot.notifications.model.ParsedMenuItem;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuItemFormatter
{
	public static ReceivedMenuItemEntity formatMenuItem(final ParsedMenuItem rawMenuItem)
	{
		final String day = rawMenuItem.getDay();
		final String course = rawMenuItem.getCourse();
		final String salad = rawMenuItem.getSalad();

		final ReceivedMenuItemEntity receivedMenuItemEntity = new ReceivedMenuItemEntity();

		if (day != null)
		{
			try
			{
				final DayOfWeek dayOfWeek = Utils.dayOfWeekFromMenuIndexString(day.trim());
				receivedMenuItemEntity.setDayOfWeek(dayOfWeek);
			}
			catch (final FoodBotNotificationException e)
			{
				log.error(e.getMessage());
			}
		}

		if (course != null && !course.trim().isEmpty())
		{
			receivedMenuItemEntity.setMainCourse(course.trim());
		}

		if (salad != null && !salad.trim().isEmpty())
		{
			receivedMenuItemEntity.setSalad(salad.trim());
		}

		return receivedMenuItemEntity;
	}
}
