package rs.laxsrbija.foodbot.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivedMenuItemDto
{
	private Long id;
	private Integer dayOfWeek;
	private String mainCourse;
	private String salad;
}
