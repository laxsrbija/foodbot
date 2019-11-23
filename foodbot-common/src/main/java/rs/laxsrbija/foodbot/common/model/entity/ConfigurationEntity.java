package rs.laxsrbija.foodbot.common.model.entity;

import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationEntity extends PlaceholderEntity
{
	@Column
	private String value;
}
