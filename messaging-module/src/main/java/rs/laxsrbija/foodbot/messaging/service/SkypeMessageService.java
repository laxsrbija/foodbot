package rs.laxsrbija.foodbot.messaging.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import kong.unirest.*;
import kong.unirest.json.JSONObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;
import rs.laxsrbija.foodbot.messaging.helper.URLHelpers;
import rs.laxsrbija.foodbot.messaging.helper.Utils;
import rs.laxsrbija.foodbot.messaging.model.Message;
import rs.laxsrbija.foodbot.messaging.model.RegistrationToken;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkypeMessageService implements MessageService
{
	private static final String HEADER_REGISTRATION_TOKEN = "RegistrationToken";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String ORIGINAL_ARRIVAL_TIME = "OriginalArrivalTime";

	final SkypeTokenService _skypeTokenService;

	@Override
	public Optional<LocalDateTime> sendMessage(final Message message)
	{
		final RegistrationToken registrationToken = _skypeTokenService.getValidRegistrationToken();

		final String outgoingMessageTimestamp = String.valueOf(Instant.now().getEpochSecond());
		final OutgoingMessage outgoingMessage = OutgoingMessage.builder()
			.content(message.getMessageText())
			.clientmessageid(outgoingMessageTimestamp).build();

		final String messagesURL = URLHelpers.getMessagesURL(registrationToken, message);
		final HttpResponse<JsonNode> messageResponse = Unirest.post(messagesURL)
			.header(HEADER_CONTENT_TYPE, "application/json")
			.header(HEADER_REGISTRATION_TOKEN, registrationToken.getToken())
			.body(outgoingMessage).asJson();

		if (messageResponse.getStatus() != 201)
		{
			throw new FoodBotMessagingException(
				"Unable to send message. Error returned: "
					+ messageResponse.getStatus() + " "
					+ messageResponse.getStatusText());
		}

		return Optional.ofNullable(getArrivalTime(messageResponse.getBody()));
	}

	private LocalDateTime getArrivalTime(final JsonNode jsonNode)
	{
		if (jsonNode == null)
		{
			log.warn("Unable to read message confirmation body, however server returned 201");
			return null;
		}

		final JSONObject object = jsonNode.getObject();
		if (object == null)
		{
			log.warn("Unable to get a message delivery confirmation, however server returned 201");
			return null;
		}

		if (!object.has(ORIGINAL_ARRIVAL_TIME))
		{
			log.warn("Unable to read message arrival time, however server returned 201");
			return null;
		}

		final long originalArrivalTime = object.getLong(ORIGINAL_ARRIVAL_TIME);
		return Utils.localDateTimeFromTimestamp(originalArrivalTime, true);
	}

	@Data
	@Builder
	private static class OutgoingMessage
	{
		@Builder.Default
		private String contenttype = "text";

		@Builder.Default
		private String messagetype = "Text";

		private String content;
		private String clientmessageid;
	}
}
