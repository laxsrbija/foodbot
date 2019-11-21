package rs.laxsrbija.foodbot.notifications.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.entity.ReceivedMenuItem;
import rs.laxsrbija.foodbot.common.entity.MenuReviewEntity;
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

	public void checkForMenuUpdates()
	{
		final List<InboundMenuEmail> emailFromServer = _inboundEmailService.getEmailFromServer();

		if (emailFromServer.isEmpty())
		{
			log.info("No new weekly menu messages");
		}

		for (final InboundMenuEmail inboundMenuEmail : emailFromServer)
		{
			processReceivedEmail(inboundMenuEmail);
		}

		log.info("New weekly menu emails have been received. Notifying reviewers...");
		final long pendingMessages = _menuReviewService.count();
		notifyReviewers(pendingMessages);
	}

	private void processReceivedEmail(final InboundMenuEmail inboundMenuEmail)
	{
		final List<ParsedMenuItem> parsedMenuItems = _menuParser.parseEmail(inboundMenuEmail);
		final List<ReceivedMenuItem> receivedMenuItems = new ArrayList<>();

		for (final ParsedMenuItem parsedMenuItem : parsedMenuItems)
		{
			final ReceivedMenuItem receivedMenuItem = MenuItemFormatter.formatMenuItem(parsedMenuItem);
			receivedMenuItems.add(receivedMenuItem);
		}

		final MenuReviewEntity weeklyMenu = new MenuReviewEntity();
		weeklyMenu.setSender(inboundMenuEmail.getSender());
		weeklyMenu.setDateSent(inboundMenuEmail.getDateSent());
		weeklyMenu.setDateReceived(inboundMenuEmail.getDateReceived());
		weeklyMenu.setRawText(inboundMenuEmail.getMessage());
		weeklyMenu.setReceivedMenuItems(receivedMenuItems);

		_menuReviewService.save(weeklyMenu);
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
