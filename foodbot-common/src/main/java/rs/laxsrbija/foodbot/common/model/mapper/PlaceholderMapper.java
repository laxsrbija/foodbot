package rs.laxsrbija.foodbot.common.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.PlaceholderDto;
import rs.laxsrbija.foodbot.common.model.entity.PlaceholderEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceholderMapper
{
	public static PlaceholderDto toDto(final PlaceholderEntity entity)
	{
		final PlaceholderDto placeholderDto = new PlaceholderDto();
		placeholderDto.setKey(entity.getKey());
		placeholderDto.setIcon(entity.getKey());
		placeholderDto.setDescription(entity.getDescription());
		return placeholderDto;
	}
}
