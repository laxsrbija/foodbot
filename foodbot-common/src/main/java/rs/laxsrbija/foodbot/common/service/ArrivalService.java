package rs.laxsrbija.foodbot.common.service;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.exception.FoodBotException;
import rs.laxsrbija.foodbot.common.model.entity.ArrivalEntity;
import rs.laxsrbija.foodbot.common.repository.ArrivalRepository;

@Service
@RequiredArgsConstructor
public class ArrivalService implements EntityServiceInterface<ArrivalEntity, Long>
{
	private final ArrivalRepository _arrivalRepository;
	private final Random _random = new Random();

	@Override
	public ArrivalEntity save(final ArrivalEntity entity)
	{
		return _arrivalRepository.save(entity);
	}

	@Override
	public List<ArrivalEntity> findAll()
	{
		final List<ArrivalEntity> arrivalEntities = new ArrayList<>();

		final Iterable<ArrivalEntity> arrivalEntityIterable = _arrivalRepository.findAll();
		arrivalEntityIterable.forEach(arrivalEntities::add);

		return arrivalEntities;
	}

	@Override
	public Optional<ArrivalEntity> findById(final Long id)
	{
		return _arrivalRepository.findById(id);
	}

	@Override
	public void deleteAll()
	{
		_arrivalRepository.deleteAll();
	}

	@Override
	public void deleteById(final Long id)
	{
		_arrivalRepository.deleteById(id);
	}

	public ArrivalEntity random()
	{
		final List<ArrivalEntity> arrivalEntities = findAll();
		final int size = arrivalEntities.size();

		if (size == 0)
		{
			throw new FoodBotException("Unable to select a random arrival message because the repository is empty");
		}

		final int randomIndex = _random.nextInt(size);
		return arrivalEntities.get(randomIndex);
	}
}
