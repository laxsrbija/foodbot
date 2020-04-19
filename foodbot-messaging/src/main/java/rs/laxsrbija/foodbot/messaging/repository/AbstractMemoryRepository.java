package rs.laxsrbija.foodbot.messaging.repository;

import java.util.Optional;

public abstract class AbstractMemoryRepository<E>
{
	protected E _data;

	public Optional<E> getData()
	{
		return Optional.ofNullable(_data);
	}

	public E saveData(final E newValue)
	{
		_data = newValue;
		return _data;
	}
}
