package rs.laxsrbija.foodbot.common.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.ArrivalDto;
import rs.laxsrbija.foodbot.common.model.entity.ArrivalEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArrivalMapper
{
	public static ArrivalDto toDto(final ArrivalEntity arrivalEntity)
	{
		return ArrivalDto.builder()
			.id(arrivalEntity.getId())
			.arrivalMessage(arrivalEntity.getArrivalMessage()).build();
	}

	public static ArrivalEntity fromDto(final ArrivalDto arrivalDto)
	{
		return ArrivalEntity.builder()
			.id(arrivalDto.getId())
			.arrivalMessage(arrivalDto.getArrivalMessage()).build();
	}
}
