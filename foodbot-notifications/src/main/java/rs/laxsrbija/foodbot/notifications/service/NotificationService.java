package rs.laxsrbija.foodbot.notifications.service;

import java.util.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.model.entity.MenuReviewEntity;
import rs.laxsrbija.foodbot.common.model.entity.ReceivedMenuItemEntity;
import rs.laxsrbija.foodbot.common.service.MenuReviewService;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration;
import rs.laxsrbija.foodbot.notifications.email.InboundEmailService;
import rs.laxsrbija.foodbot.notifications.email.OutboundEmailService;
import rs.laxsrbija.foodbot.notifications.helper.MenuItemFormatter;
import rs.laxsrbija.foodbot.notifications.model.InboundMenuEmail;
import rs.laxsrbija.foodbot.notifications.model.ParsedMenuItem;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService
{
	private static final String NOTIFICATION_SUBJECT = "FoodBot Weekly Menu Review";
	private static final String NOTIFICATION_MESSAGE = "Dear reviewer,\n\n"
		+ "New weekly menu emails are available for review.\nTotal number of pending messages: %d\n\n"
		+ "Visit the FoodBot administration panel to review them.";

	private final MenuParser _menuParser;
	private final InboundEmailService _inboundEmailService;
	private final OutboundEmailService _outboundEmailService;
	private final NotificationServiceConfiguration _configuration;
	private final MenuReviewService _menuReviewService;

	@Scheduled(cron = "${foodbot.scheduling.menu-update-check-schedule}")
	public void checkForMenuUpdates()
	{
		log.info("Checking for preliminary menu entries...");
		getNewPreliminaryMenu();
	}

	public List<MenuReviewEntity> getNewPreliminaryMenu()
	{
		final List<InboundMenuEmail> emailFromServer = _inboundEmailService.getEmailFromServer();

		if (emailFromServer.isEmpty())
		{
			log.info("No new weekly menu messages");
			return Collections.emptyList();
		}

		List<MenuReviewEntity> newReviewEntities = new ArrayList<>();
		for (final InboundMenuEmail inboundMenuEmail : emailFromServer)
		{
			final MenuReviewEntity menuReviewEntity = processReceivedEmail(inboundMenuEmail);
			newReviewEntities.add(menuReviewEntity);
		}

		log.info("New weekly menu emails have been received. Notifying reviewers...");
		final long pendingMessages = _menuReviewService.count();
		notifyReviewers(pendingMessages);

		return newReviewEntities;
	}

	private MenuReviewEntity processReceivedEmail(final InboundMenuEmail inboundMenuEmail)
	{
		final List<ParsedMenuItem> parsedMenuItems = _menuParser.parseEmail(inboundMenuEmail);
		final List<ReceivedMenuItemEntity> receivedMenuItemEntities = new ArrayList<>();

		for (final ParsedMenuItem parsedMenuItem : parsedMenuItems)
		{
			final ReceivedMenuItemEntity receivedMenuItemEntity = MenuItemFormatter.formatMenuItem(parsedMenuItem);
			receivedMenuItemEntities.add(receivedMenuItemEntity);
		}

		final MenuReviewEntity weeklyMenu = MenuReviewEntity.builder()
			.sender(inboundMenuEmail.getSender())
			.dateSent(inboundMenuEmail.getDateSent())
			.dateReceived(inboundMenuEmail.getDateReceived())
			.rawText(inboundMenuEmail.getMessage())
			.receivedMenuItemEntities(receivedMenuItemEntities).build();

		return _menuReviewService.save(weeklyMenu);
	}

	private void notifyReviewers(final long numberOfMessages)
	{
		final String message = getNotificationMessage(numberOfMessages);
		for (final String reviewer : _configuration.getMenuReviewers())
		{
			_outboundEmailService.sendEmail(reviewer, NOTIFICATION_SUBJECT, message);
		}
	}

	// TODO: Replace with Handlebars HTML in the future
	private static String getNotificationMessage(final long numberOfMessages)
	{
		return String.format(NOTIFICATION_MESSAGE, numberOfMessages);
	}
}
