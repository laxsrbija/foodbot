package rs.laxsrbija.foodbot.core.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.persistence.model.ConfigurationKeys;
import rs.laxsrbija.foodbot.persistence.model.entity.ConfigurationEntry;
import rs.laxsrbija.foodbot.persistence.model.entity.DailyMenu;
import rs.laxsrbija.foodbot.persistence.service.ConfigurationService;
import rs.laxsrbija.foodbot.persistence.service.DailyMenuService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyMenuReminderService
{
	private static final int INVALID_DAILY_REMINDER_HOUR = -1;
	private static final String HOURLY_CRON = "0 0 * * * *";

	private final ConfigurationService _configurationService;
	private final DailyMenuService _dailyMenuService;
	private final MessagingService _messagingService;
	private final PlaceholderService _placeholderService;

	@Scheduled(cron = HOURLY_CRON)
	public void sendDailyMenuReminder()
	{
		log.info("The cron job has fired");
		performReminding(false);
	}

	public void sendDailyReminderNow()
	{
		performReminding(true);
	}

	private void performReminding(final boolean overrideHoursCheck)
	{
		final LocalDateTime now = LocalDateTime.now();
		final int currentHour = now.getHour();
		final int dailyMenuReminderHour = getDailyMenuReminderHour();

		if (currentHour == dailyMenuReminderHour || overrideHoursCheck)
		{
			final DayOfWeek dayOfWeek = now.getDayOfWeek();
			final Optional<DailyMenu> dailyMenuOptional = _dailyMenuService.getDailyMenu(dayOfWeek);

			if (dailyMenuOptional.isPresent())
			{
				final DailyMenu dailyMenu = dailyMenuOptional.get();

				if (dailyMenu.getShouldBeShown())
				{
					final String reminderMessage = _placeholderService.substituteReminderPlaceholders(dailyMenu);
					log.info("Sending message: " + reminderMessage);
					_messagingService.sendMessage(reminderMessage);
				}
			}
		}
	}

	private int getDailyMenuReminderHour()
	{
		final Optional<ConfigurationEntry> configuration =
			_configurationService.getConfiguration(ConfigurationKeys.DAILY_REMINDER_HOUR);

		if (configuration.isPresent())
		{
			final String dailyHourString = configuration.get().getConfigurationValue();
			try
			{
				return Integer.parseInt(dailyHourString);
			}
			catch (final NumberFormatException e)
			{
				log.error("Unable to parse daily reminder hour: " + dailyHourString);
			}
		}

		return INVALID_DAILY_REMINDER_HOUR;
	}
}
