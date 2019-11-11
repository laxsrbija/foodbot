package rs.laxsrbija.foodbot.notifications.validator;

import java.util.Set;
import org.springframework.stereotype.Service;
import jodd.mail.EmailAddress;
import jodd.mail.ReceivedEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration;

@Slf4j
@Service
@RequiredArgsConstructor
public class InboundEmailValidator
{
	private final NotificationServiceConfiguration _notificationServiceConfiguration;

	public boolean emailIsValid(final ReceivedEmail receivedEmail)
	{
		return subjectIsValid(receivedEmail) && senderIsWhitelisted(receivedEmail);
	}

	private boolean subjectIsValid(final ReceivedEmail receivedEmail)
	{
		final String subject = receivedEmail.getSubject();
		final String expectedSubject = _notificationServiceConfiguration.getExpectedSubject();

		final boolean result = subject != null && subject.equalsIgnoreCase(expectedSubject);

		if (!result) {
			log.info("Expected the received message to have {} as subject, but got {} instead", subject, expectedSubject);
		}

		return result;
	}

	private boolean senderIsWhitelisted(final ReceivedEmail receivedEmail)
	{
		final EmailAddress sender = receivedEmail.getFrom();
		if (sender != null)
		{
			final String senderEmail = sender.getEmail();
			final Set<String> whitelistedSenders = _notificationServiceConfiguration.getWhitelistedSenders();

			final boolean result = senderEmail != null && whitelistedSenders.contains(senderEmail.trim());
			if (!result)
			{
				log.info("Address {} is not whitelisted", senderEmail);
			}
		}

		return false;
	}
}
