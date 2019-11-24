package rs.laxsrbija.foodbot.common.model.entity;

import javax.persistence.*;
import lombok.*;

@Data
@MappedSuperclass
public class PlaceholderEntity
{
	@Id
	@Column(name = "entity_key", nullable = false)
	private String key;

	@Column
	private String icon;

	@Column
	private String description;
}
