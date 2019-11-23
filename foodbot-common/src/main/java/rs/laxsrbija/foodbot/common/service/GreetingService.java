package rs.laxsrbija.foodbot.common.service;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.model.entity.GreetingEntity;
import rs.laxsrbija.foodbot.common.repository.GreetingRepository;

@Service
@RequiredArgsConstructor
public class GreetingService
{
	private final GreetingRepository _greetingRepository;
	private final Random _random = new Random();

	public GreetingEntity save(final GreetingEntity greetingEntity)
	{
		return _greetingRepository.save(greetingEntity);
	}

	public List<GreetingEntity> findAll()
	{
		final List<GreetingEntity> greetingEntities = new ArrayList<>();

		final Iterable<GreetingEntity> greetingEntityIterable = _greetingRepository.findAll();
		greetingEntityIterable.forEach(greetingEntities::add);

		return greetingEntities;
	}

	public GreetingEntity random()
	{
		final List<GreetingEntity> greetings = findAll();
		final int size = greetings.size();

		if (size == 0)
		{
			throw new FoodBotException("Unable to select a random greeting because the repository is empty");
		}

		final int randomIndex = _random.nextInt(size);
		return greetings.get(randomIndex);
	}

	public void deleteAll()
	{
		_greetingRepository.deleteAll();
	}

	public Optional<GreetingEntity> findById(final Long id)
	{
		return _greetingRepository.findById(id);
	}

	public void deleteById(final Long id)
	{
		_greetingRepository.deleteById(id);
	}
}
