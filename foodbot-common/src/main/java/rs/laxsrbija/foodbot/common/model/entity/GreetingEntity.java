package rs.laxsrbija.foodbot.common.model.entity;

import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreetingEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String greeting;
}
