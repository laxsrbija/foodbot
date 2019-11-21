package rs.laxsrbija.foodbot.common.model.entity;

import java.time.DayOfWeek;
import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@NoArgsConstructor
public class ReceivedMenuItemEntity
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
}
