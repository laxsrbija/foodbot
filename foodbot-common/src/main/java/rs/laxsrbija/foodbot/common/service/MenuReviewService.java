package rs.laxsrbija.foodbot.common.service;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.repository.MenuReviewRepository;

@Service
@RequiredArgsConstructor
public class MenuReviewService implements EntityServiceInterface<MenuReviewEntity, Long>
{
	private final MenuReviewRepository _menuReviewRepository;

	@Override
	public MenuReviewEntity save(final MenuReviewEntity menuReviewEntity) {
		return _menuReviewRepository.save(menuReviewEntity);
	}

	@Override
	public List<MenuReviewEntity> findAll()
	{
		final List<MenuReviewEntity> menuReviewEntities = new ArrayList<>();

		final Iterable<MenuReviewEntity> menuReviewEntityIterable = _menuReviewRepository.findAll();
		menuReviewEntityIterable.forEach(menuReviewEntities::add);

		return menuReviewEntities;
	}

	@Override
	public Optional<MenuReviewEntity> findById(final Long id)
	{
		return _menuReviewRepository.findById(id);
	}

	@Override
	public void deleteAll()
	{
		_menuReviewRepository.deleteAll();
	}

	@Override
	public void deleteById(final Long id)
	{
		_menuReviewRepository.deleteById(id);
	}

	public long count()
	{
		return _menuReviewRepository.count();
	}
}
