package rs.laxsrbija.foodbot.common.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.MenuEntity;
import rs.laxsrbija.foodbot.common.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService implements EntityServiceInterface<MenuEntity, DayOfWeek>
{
	private final MenuRepository _menuRepository;

	@Override
	public MenuEntity save(final MenuEntity entity)
	{
		entity.setUpdated(LocalDateTime.now());
		return _menuRepository.save(entity);
	}

	@Override
	public List<MenuEntity> findAll()
	{
		final List<MenuEntity> menuEntities = new ArrayList<>();

		final Iterable<MenuEntity> menuEntityIterable = _menuRepository.findAll();
		menuEntityIterable.forEach(menuEntities::add);

		return menuEntities;
	}

	@Override
	public Optional<MenuEntity> findById(final DayOfWeek id)
	{
		return _menuRepository.findById(id);
	}

	@Override
	public void deleteAll()
	{
		_menuRepository.deleteAll();
	}

	@Override
	public void deleteById(final DayOfWeek id)
	{
		_menuRepository.deleteById(id);
	}

	public Optional<MenuEntity> getMenuForToday()
	{
		final DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
		return findById(dayOfWeek);
	}
}
