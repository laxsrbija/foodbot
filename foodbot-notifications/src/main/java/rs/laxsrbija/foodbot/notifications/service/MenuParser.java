package rs.laxsrbija.foodbot.notifications.service;

import java.io.*;
import java.util.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.notifications.configuration.NotificationServiceConfiguration;
import rs.laxsrbija.foodbot.notifications.model.InboundMenuEmail;
import rs.laxsrbija.foodbot.notifications.model.ParsedMenuItem;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuParser
{
	private static final String COLUMN_DAY = "day";
	private static final String COLUMN_COURSE = "course";
	private static final String COLUMN_SALAD = "salad";

	private final NotificationServiceConfiguration _notificationServiceConfiguration;
	private final CsvMapper _csvMapper = new CsvMapper(new CsvFactory().enable(CsvParser.Feature.SKIP_EMPTY_LINES));

	List<ParsedMenuItem> parseEmail(final InboundMenuEmail inboundMenuEmail)
	{
		final String message = inboundMenuEmail.getMessage();

		if (message != null)
		{
			try
			{
				final String trimmedMessage = removeBlankLines(message);
				return parseEmailContent(trimmedMessage);
			}
			catch (final Exception e)
			{
				log.error("Unable to automatically parse the weekly menu", e);
			}
		}

		return Collections.emptyList();
	}

	private List<ParsedMenuItem> parseEmailContent(final String emailContent)
	{
		final List<ParsedMenuItem> parsedMenuItemList = new ArrayList<>();

		final Reader stringReader = new StringReader(emailContent);
		final CsvSchema csvSchema = getCsvSchema();
		final ObjectReader objectReader = _csvMapper.readerFor(ParsedMenuItem.class).with(csvSchema);
		final MappingIterator<Object> iterator;
		try
		{
			iterator = objectReader.readValues(stringReader);
		}
		catch (final IOException e)
		{
			log.error("Unable to read values from a string", e);
			return parsedMenuItemList;
		}

		while (iterator.hasNext())
		{
			final ParsedMenuItem parsedMenuItem = (ParsedMenuItem)iterator.next();
			parsedMenuItemList.add(parsedMenuItem);
		}

		return parsedMenuItemList;
	}

	private CsvSchema getCsvSchema()
	{
		final CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder()
			.addColumn(COLUMN_DAY, CsvSchema.ColumnType.STRING)
			.addColumn(COLUMN_COURSE, CsvSchema.ColumnType.STRING)
			.addColumn(COLUMN_SALAD, CsvSchema.ColumnType.STRING);

		final NotificationServiceConfiguration.CsvConfiguration csv = _notificationServiceConfiguration.getCsv();
		if (csv != null)
		{
			csvSchemaBuilder.setColumnSeparator(csv.getSeparator());
		}

		return csvSchemaBuilder.build().withoutHeader();
	}

	private static String removeBlankLines(final String input)
	{
		final StringJoiner stringJoiner = new StringJoiner("\n");
		final Scanner scanner = new Scanner(input);

		while (scanner.hasNextLine())
		{
			final String line = scanner.nextLine();
			final String trimmedLine = line.trim();

			if (!trimmedLine.isEmpty())
			{
				stringJoiner.add(trimmedLine);
			}
		}

		scanner.close();
		return stringJoiner.toString();
	}
}
