package rs.laxsrbija.foodbot.common.model.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigurationDto extends PlaceholderDto
{
	private String value;
}
