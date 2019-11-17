package rs.laxsrbija.foodbot.common.entity;

import java.time.DayOfWeek;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Data
@Table
@Entity
@NoArgsConstructor
public class ReceivedMenuItem
{
	private DayOfWeek dayOfWeek;
	private String mainCourse;
	private String salad;
}
