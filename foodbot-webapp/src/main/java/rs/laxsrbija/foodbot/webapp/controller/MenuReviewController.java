package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.MenuReviewDto;
import rs.laxsrbija.foodbot.common.model.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.model.mapper.MenuReviewMapper;
import rs.laxsrbija.foodbot.common.service.MenuReviewService;

@RestController
@RequestMapping(path = "/api/services/menu-review")
@RequiredArgsConstructor
public class MenuReviewController
{
	private final MenuReviewService _menuReviewService;

	@GetMapping
	public List<MenuReviewDto> getAllPendingItems()
	{
		final List<MenuReviewEntity> menuReviewEntities = _menuReviewService.findAll();
		return menuReviewEntities.stream().map(MenuReviewMapper::toDto).collect(Collectors.toList());
	}

	//TODO Delete all, delete by id, post, put
}
