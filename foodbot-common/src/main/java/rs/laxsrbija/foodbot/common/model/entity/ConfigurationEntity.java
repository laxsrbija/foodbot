package rs.laxsrbija.foodbot.common.model.entity;

import javax.persistence.*;
import lombok.*;


@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConfigurationEntity extends PlaceholderEntity
{
	@Column(name = "configuration_value")
	private String value;
}
