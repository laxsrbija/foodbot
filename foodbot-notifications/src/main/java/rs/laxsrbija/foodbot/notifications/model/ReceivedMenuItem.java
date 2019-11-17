package rs.laxsrbija.foodbot.notifications.model;

import java.time.DayOfWeek;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivedMenuItem
{
	private DayOfWeek dayOfWeek;
	private String mainCourse;
	private String salad;
}
