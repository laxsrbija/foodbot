package rs.laxsrbija.foodbot.common.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.repository.MenuReviewRepository;

@Service
@RequiredArgsConstructor
public class MenuReviewService
{
	private final MenuReviewRepository _menuReviewRepository;

	public MenuReviewEntity save(final MenuReviewEntity menuReviewEntity) {
		return _menuReviewRepository.save(menuReviewEntity);
	}

	public List<MenuReviewEntity> findAll()
	{
		final List<MenuReviewEntity> menuReviewEntities = new ArrayList<>();

		final Iterable<MenuReviewEntity> menuReviewEntityIterable = _menuReviewRepository.findAll();
		menuReviewEntityIterable.forEach(menuReviewEntities::add);

		return menuReviewEntities;
	}

	public long count()
	{
		return _menuReviewRepository.count();
	}
}
