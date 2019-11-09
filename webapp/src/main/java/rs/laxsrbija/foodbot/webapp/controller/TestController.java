package rs.laxsrbija.foodbot.webapp.controller;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.core.service.DailyMenuReminderService;
import rs.laxsrbija.foodbot.persistence.repository.ConfigurationRepository;

@Slf4j
@AllArgsConstructor
@RestController
public class TestController
{
	private final MessagingService _messagingService;
	private final ConfigurationRepository _configurationRepository;
	private final DailyMenuReminderService _dailyMenuReminderService;

	@GetMapping("")
	public String test(@RequestParam final String message)
	{
		//		final ConfigurationEntry configurationEntry = new ConfigurationEntry(ConfigurationKeys.MESSAGE, "T2eEST");
		//		_configurationRepository.save(configurationEntry);

		_messagingService.sendMessage(message);

		return null;
	}

	@GetMapping(path = "/hello")
	public void sendHello()
	{
		_messagingService.sendMessage("Hello");
	}

	@GetMapping("/now")
	public void sendReminderNow()
	{
		_dailyMenuReminderService.sendDailyReminderNow();
	}
}
