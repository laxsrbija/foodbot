package rs.laxsrbija.foodbot.common.model.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity implements Comparable<MenuEntity>
{
	@Id
	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek dayOfWeek;

	@Column
	private String mainCourse;

	@Column
	private String salad;

	@Column
	private Boolean sendReminder;

	@Column
	private LocalDateTime updated;

	@Override
	public int compareTo(final MenuEntity menuEntity)
	{
		return dayOfWeek.compareTo(menuEntity.getDayOfWeek());
	}
}
