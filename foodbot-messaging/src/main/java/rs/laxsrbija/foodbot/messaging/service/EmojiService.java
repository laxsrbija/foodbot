package rs.laxsrbija.foodbot.messaging.service;

import java.util.List;
import rs.laxsrbija.foodbot.messaging.model.EmojiSection;

public interface EmojiService
{
	List<EmojiSection> getEmojis();

	List<EmojiSection> reloadEmojiList();
}
