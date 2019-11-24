package rs.laxsrbija.foodbot.webapp.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@Profile("development")
@RequestMapping(path = "/api/services/development")
@RequiredArgsConstructor
public class DevelopmentController
{
	@GetMapping
	public String test()
	{
		return "YAY!";
	}
}
