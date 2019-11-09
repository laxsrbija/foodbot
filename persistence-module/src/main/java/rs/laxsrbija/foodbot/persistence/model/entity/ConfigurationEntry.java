package rs.laxsrbija.foodbot.persistence.model.entity;

import javax.persistence.*;
import lombok.*;
import rs.laxsrbija.foodbot.persistence.model.ConfigurationKeys;

@Entity
@Data
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationEntry
{
	@Id
	@Enumerated(EnumType.STRING)
	private ConfigurationKeys configurationKey;

	@Column
	private String configurationValue;
}
