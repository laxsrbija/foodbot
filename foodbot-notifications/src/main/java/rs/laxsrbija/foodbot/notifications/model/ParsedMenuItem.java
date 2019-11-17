package rs.laxsrbija.foodbot.notifications.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParsedMenuItem
{
	private String day;
	private String course;
	private String salad;
}