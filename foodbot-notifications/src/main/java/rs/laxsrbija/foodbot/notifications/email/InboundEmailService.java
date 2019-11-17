package rs.laxsrbija.foodbot.notifications.email;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import jodd.mail.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration.Pop3Configuration;
import rs.laxsrbija.foodbot.notifications.helper.Utils;
import rs.laxsrbija.foodbot.notifications.model.InboundMenuEmail;
import rs.laxsrbija.foodbot.notifications.validator.InboundEmailValidator;

@Slf4j
@Service
@RequiredArgsConstructor
public class InboundEmailService
{
	private static final String MEDIA_TYPE_TEXT_PLAIN = "text/plain";
	private static final String CONTENT_TYPE_MULTIPART = "multipart/alternative";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";

	private final NotificationServiceConfiguration _notificationServiceConfiguration;
	private final InboundEmailValidator _emailValidator;

	public List<InboundMenuEmail> getEmailFromServer()
	{
		final Pop3Server popServer = getPopServer();
		final ReceiveMailSession session = popServer.createSession();

		try
		{
			session.open();
			final ReceivedEmail[] receivedEmails = session.receiveEmailAndMarkSeen();

			final int messageCount = session.getMessageCount();
			log.info("Fetched {} new email{} from the server", messageCount, messageCount == 1 ? "" : "s");

			return getReceivedMessages(receivedEmails);
		}
		catch (final MailException e)
		{
			log.error("Unable to fetch email from the POP3 server", e);
			return Collections.emptyList();
		}
		finally
		{
			session.close();
		}
	}

	private List<InboundMenuEmail> getReceivedMessages(final ReceivedEmail[] receivedEmails)
	{
		final List<InboundMenuEmail> emails = new ArrayList<>();

		if (receivedEmails != null)
		{
			for (final ReceivedEmail receivedEmail : receivedEmails)
			{
				if (!_emailValidator.emailIsValid(receivedEmail))
				{
					log.info("The received email was rejected");
					continue;
				}

				final InboundMenuEmail inboundMenuEmail = InboundMenuEmail.builder()
					.sender(receivedEmail.getFrom().getEmail())
					.dateSent(Utils.fromDate(receivedEmail.getSentDate()))
					.dateReceived(LocalDateTime.now())
					.message(getEmailBody(receivedEmail)).build();
				emails.add(inboundMenuEmail);
			}
		}

		return emails;
	}

	private String getEmailBody(final ReceivedEmail receivedEmail)
	{
		final List<EmailMessage> emailMessages = receivedEmail.getAllMessages();
		final String contentType = receivedEmail.getHeader(HEADER_CONTENT_TYPE);

		if (contentType != null && contentType.contains(CONTENT_TYPE_MULTIPART))
		{
			for (final EmailMessage emailMessage : emailMessages)
			{
				final String mimeType = emailMessage.getMimeType();
				if (MEDIA_TYPE_TEXT_PLAIN.equalsIgnoreCase(mimeType))
				{
					return emailMessage.getContent();
				}
			}
		}
		else if (!emailMessages.isEmpty())
		{
			// Return message in any other format, which will be manually parsed by the reviewer
			return emailMessages.get(0).getContent();
		}

		log.warn("Unable to find email content");
		return null;
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
