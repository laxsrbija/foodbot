package rs.laxsrbija.foodbot.common.service;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.PreliminaryMenuEntity;
import rs.laxsrbija.foodbot.common.repository.PreliminaryMenuRepository;

@Service
@RequiredArgsConstructor
public class PreliminaryMenuService implements EntityServiceInterface<PreliminaryMenuEntity, Long>
{
	private final PreliminaryMenuRepository _preliminaryMenuRepository;

	@Override
	public PreliminaryMenuEntity save(final PreliminaryMenuEntity preliminaryMenuEntity) {
		return _preliminaryMenuRepository.save(preliminaryMenuEntity);
	}

	@Override
	public List<PreliminaryMenuEntity> findAll()
	{
		final List<PreliminaryMenuEntity> menuReviewEntities = new ArrayList<>();

		final Iterable<PreliminaryMenuEntity> menuReviewEntityIterable = _preliminaryMenuRepository.findAll();
		menuReviewEntityIterable.forEach(menuReviewEntities::add);

		return menuReviewEntities;
	}

	@Override
	public Optional<PreliminaryMenuEntity> findById(final Long id)
	{
		return _preliminaryMenuRepository.findById(id);
	}

	@Override
	public void deleteAll()
	{
		_preliminaryMenuRepository.deleteAll();
	}

	@Override
	public void deleteById(final Long id)
	{
		_preliminaryMenuRepository.deleteById(id);
	}

	public long count()
	{
		return _preliminaryMenuRepository.count();
	}
}
