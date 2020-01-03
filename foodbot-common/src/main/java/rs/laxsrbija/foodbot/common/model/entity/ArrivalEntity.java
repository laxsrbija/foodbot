package rs.laxsrbija.foodbot.common.model.entity;

import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String arrivalMessage;
}
