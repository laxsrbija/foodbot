package rs.laxsrbija.foodbot.common.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.GreetingDto;
import rs.laxsrbija.foodbot.common.model.entity.GreetingEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GreetingMapper
{
	public static GreetingDto toDto(final GreetingEntity greetingEntity)
	{
		return GreetingDto.builder()
			.id(greetingEntity.getId())
			.greeting(greetingEntity.getGreeting()).build();
	}

	public static GreetingEntity fromDto(final GreetingDto greetingDto)
	{
		return GreetingEntity.builder()
			.id(greetingDto.getId())
			.greeting(greetingDto.getGreeting()).build();
	}
}
