package rs.laxsrbija.foodbot.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingDto
{
	private Long id;
	private String greeting;
}
