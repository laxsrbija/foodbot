package rs.laxsrbija.foodbot.persistence.model.entity;

import java.time.DayOfWeek;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Data
@Table
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DailyMenu implements Comparable<DailyMenu>
{
	@Id
	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek dayOfWeek;

	@Column
	private Boolean shouldBeShown = true;

	@Column
	private String mainCourse;

	@Column
	private String salad;

	@Override
	@JsonIgnore
	public int compareTo(final DailyMenu dailyMenu)
	{
		return dayOfWeek.compareTo(dailyMenu.getDayOfWeek());
	}
}
