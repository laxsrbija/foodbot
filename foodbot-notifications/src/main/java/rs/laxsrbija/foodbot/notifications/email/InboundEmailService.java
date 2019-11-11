package rs.laxsrbija.foodbot.notifications.email;

import org.springframework.stereotype.Service;
import jodd.mail.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration.Pop3Configuration;
import rs.laxsrbija.foodbot.notifications.exception.FoodBotNotificationException;
import rs.laxsrbija.foodbot.notifications.validator.InboundEmailValidator;

@Slf4j
@Service
@RequiredArgsConstructor
public class InboundEmailService
{
	private final NotificationServiceConfiguration _notificationServiceConfiguration;
	private final InboundEmailValidator _emailValidator;

	public void getEmailFromServer()
	{
		final Pop3Server popServer = getPopServer();
		final ReceiveMailSession session = popServer.createSession();

		try
		{
			session.open();
			final ReceivedEmail[] receivedEmails = session.receiveEmail();

			final int messageCount = session.getMessageCount();
			log.info("Fetched {} new email{} from the server", messageCount, messageCount == 1 ? "" : "s");

			getReceivedMessages(receivedEmails);
		}
		catch (final MailException e)
		{
			throw new FoodBotNotificationException("Unable to fetch email from the POP3 server", e);
		}
		finally
		{
			session.close();
		}
	}

	private void getReceivedMessages(final ReceivedEmail[] receivedEmails)
	{
		if (receivedEmails == null) {
			return;
		}

		for (final ReceivedEmail receivedEmail : receivedEmails)
		{
			if (!_emailValidator.emailIsValid(receivedEmail))
			{
				log.info("The received email was rejected");
				continue;
			}

			// TODO parse to WeeklyMenuMessage

			System.out.println("FROM:" + receivedEmail.getFrom().getEmail());
			System.out.println("SUBJECT:" + receivedEmail.getSubject());
		}
	}

	private Pop3Server getPopServer()
	{
		final Pop3Configuration pop3 = _notificationServiceConfiguration.getPop3();

		return new Pop3SslServer(
			pop3.getHost(),
			pop3.getPort(),
			pop3.getUsername(),
			pop3.getPassword());
	}
}
