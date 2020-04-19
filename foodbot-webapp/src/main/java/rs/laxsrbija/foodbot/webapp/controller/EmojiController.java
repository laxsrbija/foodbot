package rs.laxsrbija.foodbot.webapp.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.messaging.model.EmojiSection;
import rs.laxsrbija.foodbot.messaging.service.EmojiService;

@RestController
@RequestMapping(path = "/api/services/emoji")
@RequiredArgsConstructor
public class EmojiController
{
	private final EmojiService _emojiService;

	@GetMapping
	public List<EmojiSection> getEmoji() {
		return _emojiService.getEmojis();
	}

	@PostMapping
	public List<EmojiSection> reloadEmoji() {
		return _emojiService.reloadEmojiList();
	}
}
