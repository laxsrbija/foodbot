package rs.laxsrbija.foodbot.common.model.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.helper.DateTimeConverter;
import rs.laxsrbija.foodbot.common.model.dto.MenuReviewDto;
import rs.laxsrbija.foodbot.common.model.dto.ReceivedMenuItemDto;
import rs.laxsrbija.foodbot.common.model.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.model.entity.ReceivedMenuItemEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuReviewMapper
{
	public static MenuReviewDto toDto(final MenuReviewEntity menuReviewEntity)
	{
		final MenuReviewDto.MenuReviewDtoBuilder dtoBuilder = MenuReviewDto.builder()
			.id(menuReviewEntity.getId())
			.sender(menuReviewEntity.getSender())
			.rawText(menuReviewEntity.getRawText());

		final LocalDateTime dateReceived = menuReviewEntity.getDateReceived();
		if (dateReceived != null)
		{
			dtoBuilder.dateReceived(DateTimeConverter.fromLocalDateTime(dateReceived));
		}

		final LocalDateTime dateSent = menuReviewEntity.getDateSent();
		if (dateSent != null)
		{
			dtoBuilder.dateSent(DateTimeConverter.fromLocalDateTime(dateSent));
		}

		final List<ReceivedMenuItemEntity> receivedMenuItemEntities = menuReviewEntity.getReceivedMenuItemEntities();
		if (receivedMenuItemEntities != null)
		{
			final List<ReceivedMenuItemDto> receivedMenuItems =
				receivedMenuItemEntities.stream()
					.map(ReceivedMenuItemMapper::toDto)
					.collect(Collectors.toList());
			dtoBuilder.receivedMenuItems(receivedMenuItems);
		}

		return dtoBuilder.build();
	}

	public static MenuReviewEntity fromDto(final MenuReviewDto menuReviewDto)
	{
		final MenuReviewEntity.MenuReviewEntityBuilder entityBuilder = MenuReviewEntity.builder()
			.id(menuReviewDto.getId())
			.sender(menuReviewDto.getSender())
			.rawText(menuReviewDto.getRawText());

		final String dateReceived = menuReviewDto.getDateReceived();
		if (dateReceived != null && !dateReceived.trim().isEmpty())
		{
			final LocalDateTime localDateTime = DateTimeConverter.toLocalDateTime(dateReceived);
			entityBuilder.dateReceived(localDateTime);
		}

		final String dateSent = menuReviewDto.getDateSent();
		if (dateSent != null && !dateSent.trim().isEmpty())
		{
			final LocalDateTime localDateTime = DateTimeConverter.toLocalDateTime(dateSent);
			entityBuilder.dateSent(localDateTime);
		}

		final List<ReceivedMenuItemDto> receivedMenuItems = menuReviewDto.getReceivedMenuItems();
		if (receivedMenuItems != null)
		{
			final List<ReceivedMenuItemEntity> receivedMenuItemEntities =
				receivedMenuItems.stream()
					.map(ReceivedMenuItemMapper::fromDto)
					.collect(Collectors.toList());
			entityBuilder.receivedMenuItemEntities(receivedMenuItemEntities);
		}

		return entityBuilder.build();
	}
}
