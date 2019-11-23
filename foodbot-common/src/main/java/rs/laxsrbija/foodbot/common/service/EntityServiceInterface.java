package rs.laxsrbija.foodbot.common.service;

import java.util.List;
import java.util.Optional;

public interface EntityServiceInterface<E, I>
{
	E save(E entity);

	List<E> findAll();

	Optional<E> findById(I id);

	void deleteAll();

	void deleteById(I id);
}
