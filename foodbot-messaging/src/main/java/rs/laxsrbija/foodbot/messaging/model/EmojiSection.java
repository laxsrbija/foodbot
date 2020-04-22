package rs.laxsrbija.foodbot.messaging.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmojiSection
{
	private String sectionName;
	private List<Emoji> emojiList;
}
