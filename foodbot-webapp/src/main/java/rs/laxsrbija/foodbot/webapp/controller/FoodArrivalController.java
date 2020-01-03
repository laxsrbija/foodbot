package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.ArrivalDto;
import rs.laxsrbija.foodbot.common.model.entity.ArrivalEntity;
import rs.laxsrbija.foodbot.common.model.mapper.ArrivalMapper;
import rs.laxsrbija.foodbot.common.service.ArrivalService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/api/services/arrival")
@RequiredArgsConstructor
public class FoodArrivalController
{
	private final ArrivalService _arrivalService;

	@GetMapping
	public List<ArrivalDto> getAllArrivalMessages()
	{
		return _arrivalService.findAll().stream()
			.map(ArrivalMapper::toDto)
			.collect(Collectors.toList());
	}

	@PostMapping
	public ArrivalDto saveArrivalMessage(@RequestBody final ArrivalDto newArrival)
	{
		final ArrivalEntity savedArrivalMessage = _arrivalService.save(ArrivalMapper.fromDto(newArrival));
		return ArrivalMapper.toDto(savedArrivalMessage);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllArrivalMessages()
	{
		_arrivalService.deleteAll();
	}

	@GetMapping("/{id}")
	public ArrivalDto getById(@PathVariable("id") final Long id)
	{
		final ArrivalEntity arrivalEntity = _arrivalService.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Unable to find an arrival message with ID " + id));
		return ArrivalMapper.toDto(arrivalEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteArrivalMessageById(@PathVariable("id") final Long id)
	{
		_arrivalService.deleteById(id);
	}

	@GetMapping("/random")
	public ArrivalDto getRandomArrivalMessage()
	{
		return ArrivalMapper.toDto(_arrivalService.random());
	}
}
