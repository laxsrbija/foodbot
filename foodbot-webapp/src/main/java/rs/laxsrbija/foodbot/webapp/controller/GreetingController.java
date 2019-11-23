package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.model.dto.GreetingDto;
import rs.laxsrbija.foodbot.common.model.entity.GreetingEntity;
import rs.laxsrbija.foodbot.common.model.mapper.GreetingMapper;
import rs.laxsrbija.foodbot.common.service.GreetingService;
import rs.laxsrbija.foodbot.webapp.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/api/services/greetings")
@RequiredArgsConstructor
public class GreetingController
{
	private final GreetingService _greetingService;

	@GetMapping
	public List<GreetingDto> getAllGreetings()
	{
		return _greetingService.findAll().stream()
			.map(GreetingMapper::toDto)
			.collect(Collectors.toList());
	}

	@PostMapping
	public GreetingDto saveGreeting(@RequestBody final GreetingDto newGreeting)
	{
		final GreetingEntity savedGreeting = _greetingService.save(GreetingMapper.fromDto(newGreeting));
		return GreetingMapper.toDto(savedGreeting);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllGreetings()
	{
		_greetingService.deleteAll();
	}

	@GetMapping("/{id}")
	public GreetingDto getById(@PathVariable("id") final Long id)
	{
		final GreetingEntity greetingEntity = _greetingService.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Unable to find a greeting with ID " + id));
		return GreetingMapper.toDto(greetingEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGreetingById(@PathVariable("id") final Long id)
	{
		_greetingService.deleteById(id);
	}

	@GetMapping("/random")
	public GreetingDto getRandomGreeting()
	{
		return GreetingMapper.toDto(_greetingService.random());
	}
}
