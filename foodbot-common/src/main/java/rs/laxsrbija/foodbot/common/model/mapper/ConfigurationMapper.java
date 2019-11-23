package rs.laxsrbija.foodbot.common.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.ConfigurationDto;
import rs.laxsrbija.foodbot.common.model.entity.ConfigurationEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigurationMapper
{
	public static ConfigurationDto toDto(final ConfigurationEntity configurationEntity)
	{
		final ConfigurationDto dto = new ConfigurationDto();
		dto.setKey(configurationEntity.getKey());
		dto.setDescription(configurationEntity.getDescription());
		dto.setIcon(configurationEntity.getIcon());
		dto.setValue(configurationEntity.getValue());
		return dto;
	}

	public static ConfigurationEntity fromDto(final ConfigurationDto configurationDto)
	{
		final ConfigurationEntity entity = new ConfigurationEntity();
		entity.setKey(configurationDto.getKey());
		entity.setDescription(configurationDto.getDescription());
		entity.setIcon(configurationDto.getIcon());
		entity.setValue(configurationDto.getValue());
		return entity;
	}
}
