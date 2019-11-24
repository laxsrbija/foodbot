package rs.laxsrbija.foodbot.common.model.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.helper.DateTimeConverter;
import rs.laxsrbija.foodbot.common.model.dto.PreliminaryMenuDto;
import rs.laxsrbija.foodbot.common.model.dto.ReceivedMenuItemDto;
import rs.laxsrbija.foodbot.common.model.entity.PreliminaryMenuEntity;
import rs.laxsrbija.foodbot.common.model.entity.ReceivedMenuItemEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PreliminaryMenuMapper
{
	public static PreliminaryMenuDto toDto(final PreliminaryMenuEntity preliminaryMenuEntity)
	{
		final PreliminaryMenuDto.PreliminaryMenuDtoBuilder dtoBuilder = PreliminaryMenuDto.builder()
			.id(preliminaryMenuEntity.getId())
			.sender(preliminaryMenuEntity.getSender())
			.rawText(preliminaryMenuEntity.getRawText());

		final LocalDateTime dateReceived = preliminaryMenuEntity.getDateReceived();
		if (dateReceived != null)
		{
			dtoBuilder.dateReceived(DateTimeConverter.fromLocalDateTime(dateReceived));
		}

		final LocalDateTime dateSent = preliminaryMenuEntity.getDateSent();
		if (dateSent != null)
		{
			dtoBuilder.dateSent(DateTimeConverter.fromLocalDateTime(dateSent));
		}

		final List<ReceivedMenuItemEntity> receivedMenuItemEntities = preliminaryMenuEntity.getReceivedMenuItemEntities();
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

	public static PreliminaryMenuEntity fromDto(final PreliminaryMenuDto preliminaryMenuDto)
	{
		final PreliminaryMenuEntity.PreliminaryMenuEntityBuilder entityBuilder = PreliminaryMenuEntity.builder()
			.id(preliminaryMenuDto.getId())
			.sender(preliminaryMenuDto.getSender())
			.rawText(preliminaryMenuDto.getRawText());

		final String dateReceived = preliminaryMenuDto.getDateReceived();
		if (dateReceived != null && !dateReceived.trim().isEmpty())
		{
			final LocalDateTime localDateTime = DateTimeConverter.toLocalDateTime(dateReceived);
			entityBuilder.dateReceived(localDateTime);
		}

		final String dateSent = preliminaryMenuDto.getDateSent();
		if (dateSent != null && !dateSent.trim().isEmpty())
		{
			final LocalDateTime localDateTime = DateTimeConverter.toLocalDateTime(dateSent);
			entityBuilder.dateSent(localDateTime);
		}

		final List<ReceivedMenuItemDto> receivedMenuItems = preliminaryMenuDto.getReceivedMenuItems();
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
