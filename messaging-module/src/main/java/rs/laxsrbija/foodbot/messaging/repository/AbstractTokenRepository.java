package rs.laxsrbija.foodbot.messaging.repository;

import java.util.Optional;

public abstract class AbstractTokenRepository<E>
{
	protected E token;

	public Optional<E> getToken()
	{
		return Optional.ofNullable(token);
	}

	public E saveToken(final E newValue)
	{
		token = newValue;
		return token;
	}
}
