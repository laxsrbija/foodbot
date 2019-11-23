package rs.laxsrbija.foodbot.common.model.mapper;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.helper.DateTimeConverter;
import rs.laxsrbija.foodbot.common.helper.DayOfWeekUtils;
import rs.laxsrbija.foodbot.common.model.dto.MenuDto;
import rs.laxsrbija.foodbot.common.model.entity.MenuEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuMapper
{
	public static MenuDto toDto(final MenuEntity entity)
	{
		final MenuDto.MenuDtoBuilder dtoBuilder = MenuDto.builder()
			.dayOfWeek(entity.getDayOfWeek().getValue())
			.mainCourse(entity.getMainCourse())
			.salad(entity.getSalad())
			.sendReminder(entity.getSendReminder());

		final LocalDateTime updated = entity.getUpdated();
		if (updated != null)
		{
			dtoBuilder.updated(DateTimeConverter.fromLocalDateTime(updated));
		}

		return dtoBuilder.build();
	}

	public static MenuEntity fromDto(final MenuDto dto)
	{
		final MenuEntity.MenuEntityBuilder entityBuilder = MenuEntity.builder()
			.dayOfWeek(DayOfWeekUtils.dayOfWeekFromIndex(dto.getDayOfWeek()))
			.mainCourse(dto.getMainCourse())
			.salad(dto.getSalad())
			.sendReminder(dto.getSendReminder());

		final String updated = dto.getUpdated();
		if (updated != null)
		{
			entityBuilder.updated(DateTimeConverter.toLocalDateTime(updated));
		}

		return entityBuilder.build();
	}
}
