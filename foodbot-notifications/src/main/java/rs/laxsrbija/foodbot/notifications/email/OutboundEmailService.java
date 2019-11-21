package rs.laxsrbija.foodbot.notifications.email;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboundEmailService
{
	private final NotificationServiceConfiguration _configuration;

	public void sendEmail(final String recipient, final String subject, final String content)
	{
		final NotificationServiceConfiguration.SmtpConfiguration smtp = _configuration.getSmtp();
		final String senderAddress = smtp.getUsername();
		final String senderDisplayName = smtp.getUsername();

		final Email email = EmailBuilder.startingBlank()
			.to(recipient)
			.from(senderDisplayName, senderAddress)
			.withSubject(subject)
			.withPlainText(content)
			.buildEmail();

		final Mailer mailer = getMailer();
		mailer.sendMail(email);
		log.info("Successfully sent an email");
	}

	private Mailer getMailer()
	{
		final NotificationServiceConfiguration.SmtpConfiguration smtp = _configuration.getSmtp();
		return MailerBuilder
			.withSMTPServerHost(smtp.getHost())
			.withSMTPServerPort(smtp.getPort())
			.withSMTPServerUsername(smtp.getUsername())
			.withSMTPServerPassword(smtp.getPassword())
			.withTransportStrategy(TransportStrategy.SMTPS).buildMailer();
	}
}
