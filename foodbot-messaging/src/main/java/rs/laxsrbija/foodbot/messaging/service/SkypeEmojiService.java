package rs.laxsrbija.foodbot.messaging.service;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.model.Emoji;
import rs.laxsrbija.foodbot.messaging.model.EmojiSection;
import rs.laxsrbija.foodbot.messaging.repository.EmojiSectionsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkypeEmojiService implements EmojiService, ApplicationRunner
{
	private static final String EMOJI_LIST_URL = "https://support.skype.com/en/faq/FA12330/what-is-the-full-list-of-emoticons";
	private static final String SELECTOR_EMOJI_SECTIONS = "div.faqBoxWrapper";
	private static final String SELECTOR_EMOJI_SECTION_NAME = ".titleLink > span.titleText";
	private static final String SELECTOR_EMOJI_ROWS = "table > tbody > tr:gt(0)";
	private static final String SELECTOR_EMOJI_ROW = "td > img";
	private static final String SELECTOR_EMOJI_NAME = "td:eq(1)";
	private static final String SELECTOR_EMOJI_CODE = "td:eq(2)";

	private final EmojiSectionsRepository _emojiSectionsRepository;

	@Override
	public List<EmojiSection> getEmojis()
	{
		return _emojiSectionsRepository.getData().orElseGet(ArrayList::new);
	}

	@Override
	public void run(final ApplicationArguments args)
	{
		// Load the emoji list on application startup
		reloadEmojiList();
	}

	@Override
	public List<EmojiSection> reloadEmojiList()
	{
		final HttpResponse<String> httpResponse = Unirest.get(EMOJI_LIST_URL).asString();

		if (!httpResponse.isSuccess())
		{
			log.warn("Unable to fetch a list of emojis: {}", httpResponse.getStatusText());
			return Collections.emptyList();
		}

		final String responseHtml = httpResponse.getBody();
		final Document document = Jsoup.parse(responseHtml);
		final Elements sections = document.select(SELECTOR_EMOJI_SECTIONS);

		final List<EmojiSection> emojiSections = new ArrayList<>();
		for (final Element section : sections)
		{
			final String sectionName = section.selectFirst(SELECTOR_EMOJI_SECTION_NAME).html();

			final List<Emoji> emojiList = new ArrayList<>();
			final Elements emojiRows = section.select(SELECTOR_EMOJI_ROWS);
			for (final Element emojiRow : emojiRows)
			{
				final String emojiImageSource = emojiRow.selectFirst(SELECTOR_EMOJI_ROW).attr("src");
				final String emojiName = emojiRow.selectFirst(SELECTOR_EMOJI_NAME).html();
				final String emojiCode = emojiRow.selectFirst(SELECTOR_EMOJI_CODE).html();

				final Emoji emoji = Emoji.builder()
					.name(emojiName)
					.code(getEmojiCode(emojiCode))
					.source(emojiImageSource)
					.build();
				emojiList.add(emoji);
			}

			final EmojiSection emojiSection = EmojiSection.builder()
				.sectionName(sectionName)
				.emojiList(emojiList)
				.build();
			emojiSections.add(emojiSection);
		}

		log.info("Reloaded the emoji list");
		return _emojiSectionsRepository.saveData(emojiSections);
	}

	private String getEmojiCode(final String emojiCodes)
	{
		final String unescapedCodes = StringEscapeUtils.unescapeHtml4(emojiCodes);
		final String[] split = StringUtils.split(unescapedCodes);

		final String code = split.length > 0 ? split[0].trim() : unescapedCodes;
		return StringUtils.strip(code);
	}
}
