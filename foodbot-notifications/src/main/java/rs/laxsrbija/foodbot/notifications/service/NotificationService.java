package rs.laxsrbija.foodbot.notifications.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.common.entity.ReceivedMenuItem;
import rs.laxsrbija.foodbot.common.entity.WeeklyMenuNotification;
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
	private final MenuParserService _menuParserService;
	private final InboundEmailService _inboundEmailService;
	private final OutboundEmailService _outboundEmailService;

	public void checkForMenuUpdates()
	{
		final List<InboundMenuEmail> emailFromServer = _inboundEmailService.getEmailFromServer();

		for (final InboundMenuEmail inboundMenuEmail : emailFromServer)
		{
			final List<ParsedMenuItem> parsedMenuItems = _menuParserService.parseEmail(inboundMenuEmail);
			final List<ReceivedMenuItem> receivedMenuItems = new ArrayList<>();

			for (final ParsedMenuItem parsedMenuItem : parsedMenuItems)
			{
				final ReceivedMenuItem receivedMenuItem = MenuItemFormatter.formatMenuItem(parsedMenuItem);
				receivedMenuItems.add(receivedMenuItem);
			}

			final WeeklyMenuNotification weeklyMenu = WeeklyMenuNotification.builder()
				.id(123)
				.sender(inboundMenuEmail.getSender())
				.dateSent(inboundMenuEmail.getDateSent())
				.dateReceived(inboundMenuEmail.getDateReceived())
				.rawText(inboundMenuEmail.getMessage())
				.receivedMenuItems(receivedMenuItems).build();
			log.info(weeklyMenu.toString());
		}

		if (!emailFromServer.isEmpty())
		{
			final String message = "Hello, new menu emails have been received.\n"
				+ "Total number of new emails: " + emailFromServer.size();
			//_outboundEmailService.sendEmail("lazars@niri-ic.com", "Weekly menu received", message);
		}
	}
}
