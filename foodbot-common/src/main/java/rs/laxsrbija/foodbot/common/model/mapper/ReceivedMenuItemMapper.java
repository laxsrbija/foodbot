package rs.laxsrbija.foodbot.common.model.mapper;

import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.helper.DateUtils;
import rs.laxsrbija.foodbot.common.model.dto.ReceivedMenuItemDto;
import rs.laxsrbija.foodbot.common.model.entity.ReceivedMenuItemEntity;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReceivedMenuItemMapper
{
	static ReceivedMenuItemDto toDto(final ReceivedMenuItemEntity receivedMenuItemEntity)
	{
		final ReceivedMenuItemDto.ReceivedMenuItemDtoBuilder dtoBuilder = ReceivedMenuItemDto.builder()
			.id(receivedMenuItemEntity.getId())
			.mainCourse(receivedMenuItemEntity.getMainCourse())
			.salad(receivedMenuItemEntity.getSalad());

		final DayOfWeek dayOfWeek = receivedMenuItemEntity.getDayOfWeek();
		if (dayOfWeek != null)
		{
			dtoBuilder.dayOfWeek(dayOfWeek.getValue());
		}

		return dtoBuilder.build();
	}

	static ReceivedMenuItemEntity fromDto(final ReceivedMenuItemDto receivedMenuItemDto)
	{
		final ReceivedMenuItemEntity.ReceivedMenuItemEntityBuilder entityBuilder = ReceivedMenuItemEntity.builder()
			.id(receivedMenuItemDto.getId())
			.mainCourse(receivedMenuItemDto.getMainCourse())
			.salad(receivedMenuItemDto.getSalad());

		final Integer dayOfWeekIndex = receivedMenuItemDto.getDayOfWeek();
		if (dayOfWeekIndex != null)
		{
			try
			{
				entityBuilder.dayOfWeek(DateUtils.dayOfWeekFromIndex(dayOfWeekIndex));
			}
			catch (final FoodBotException e)
			{
				log.warn(e.getMessage());
			}
		}

		return entityBuilder.build();
	}
}
