package rs.laxsrbija.foodbot.common.model.entity;

import java.time.DayOfWeek;
import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedMenuItemEntity implements Comparable<ReceivedMenuItemEntity>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private DayOfWeek dayOfWeek;

	@Column
	private String mainCourse;

	@Column
	private String salad;

	@Override
	public int compareTo(final ReceivedMenuItemEntity dailyMenu)
	{
		return dayOfWeek.compareTo(dailyMenu.getDayOfWeek());
	}
}
