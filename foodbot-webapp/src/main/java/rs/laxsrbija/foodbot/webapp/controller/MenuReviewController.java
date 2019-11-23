package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.MenuReviewDto;
import rs.laxsrbija.foodbot.common.model.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.model.mapper.MenuReviewMapper;
import rs.laxsrbija.foodbot.common.service.MenuReviewService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/api/services/review")
@RequiredArgsConstructor
public class MenuReviewController
{
	private final MenuReviewService _menuReviewService;

	@GetMapping
	public List<MenuReviewDto> getAllPendingItems()
	{
		return _menuReviewService.findAll().stream()
			.map(MenuReviewMapper::toDto)
			.collect(Collectors.toList());
	}

	@PostMapping
	public MenuReviewDto saveMenuReview(@RequestBody final MenuReviewDto newMenuReview)
	{
		final MenuReviewEntity savedEntity = _menuReviewService.save(MenuReviewMapper.fromDto(newMenuReview));
		return MenuReviewMapper.toDto(savedEntity);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllPendingItems()
	{
		_menuReviewService.deleteAll();
	}

	@GetMapping("/{id}")
	public MenuReviewDto getById(@PathVariable("id") final Long id)
	{
		final MenuReviewEntity menuReviewEntity = _menuReviewService.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Unable to find a review item with ID " + id));
		return MenuReviewMapper.toDto(menuReviewEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReviewItemById(@PathVariable("id") final Long id)
	{
		_menuReviewService.deleteById(id);
	}

	@GetMapping("/count")
	public Long getPendingReviewItems()
	{
		return _menuReviewService.count();
	}
}
