package rs.laxsrbija.foodbot.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto
{
	private Integer dayOfWeek;
	private String mainCourse;
	private String salad;
	@Builder.Default
	private Boolean sendReminder = true;
	private String updated;
}
