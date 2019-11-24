package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.MenuDto;
import rs.laxsrbija.foodbot.common.model.dto.PreliminaryMenuDto;
import rs.laxsrbija.foodbot.common.model.entity.PreliminaryMenuEntity;
import rs.laxsrbija.foodbot.common.model.mapper.MenuMapper;
import rs.laxsrbija.foodbot.common.model.mapper.PreliminaryMenuMapper;
import rs.laxsrbija.foodbot.common.service.PreliminaryMenuService;
import rs.laxsrbija.foodbot.notifications.service.NotificationService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;
import rs.laxsrbija.foodbot.webapp.service.PreliminaryMenuPublisher;

@RestController
@RequestMapping(path = "/api/services/review")
@RequiredArgsConstructor
public class PreliminaryMenuController
{
	private final PreliminaryMenuService _preliminaryMenuService;
	private final PreliminaryMenuPublisher _preliminaryMenuPublisher;
	private final NotificationService _notificationService;

	@GetMapping
	public List<PreliminaryMenuDto> getAllPendingItems()
	{
		return _preliminaryMenuService.findAll().stream()
			.map(PreliminaryMenuMapper::toDto)
			.collect(Collectors.toList());
	}

	@PostMapping
	public PreliminaryMenuDto savePreliminaryMenu(@RequestBody final PreliminaryMenuDto newMenuReview)
	{
		final PreliminaryMenuEntity savedEntity = _preliminaryMenuService.save(PreliminaryMenuMapper.fromDto(newMenuReview));
		return PreliminaryMenuMapper.toDto(savedEntity);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllPendingItems()
	{
		_preliminaryMenuService.deleteAll();
	}

	@GetMapping("/{id}")
	public PreliminaryMenuDto getById(@PathVariable("id") final Long id)
	{
		final PreliminaryMenuEntity preliminaryMenuEntity = _preliminaryMenuService.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Unable to find a review item with ID " + id));
		return PreliminaryMenuMapper.toDto(preliminaryMenuEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePreliminaryMenuById(@PathVariable("id") final Long id)
	{
		_preliminaryMenuService.deleteById(id);
	}

	@GetMapping("/count")
	public Long getPendingReviewItems()
	{
		return _preliminaryMenuService.count();
	}

	@GetMapping("/publish")
	public List<MenuDto> publishMenu(@RequestBody final PreliminaryMenuDto dto)
	{
		return _preliminaryMenuPublisher.publishReviewMenu(PreliminaryMenuMapper.fromDto(dto)).stream()
			.map(MenuMapper::toDto)
			.collect(Collectors.toList());
	}

	@GetMapping("/check")
	public List<PreliminaryMenuDto> checkForPreliminaryMenuUpdates()
	{
		return _notificationService.getNewPreliminaryMenu().stream()
			.map(PreliminaryMenuMapper::toDto)
			.collect(Collectors.toList());
	}
}
