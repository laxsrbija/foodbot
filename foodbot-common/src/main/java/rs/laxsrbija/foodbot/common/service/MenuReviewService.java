package rs.laxsrbija.foodbot.common.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.repository.MenuReviewRepository;

@Service
@RequiredArgsConstructor
public class MenuReviewService
{
	private final MenuReviewRepository _menuReviewRepository;

	public MenuReviewEntity save(final MenuReviewEntity menuReviewEntity) {
		return _menuReviewRepository.save(menuReviewEntity);
	}

	public Iterable<MenuReviewEntity> findAll()
	{
		return _menuReviewRepository.findAll();
	}

	public long count()
	{
		return _menuReviewRepository.count();
	}
}
