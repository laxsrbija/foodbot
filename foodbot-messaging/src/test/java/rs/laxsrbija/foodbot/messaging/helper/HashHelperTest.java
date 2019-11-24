package rs.laxsrbija.foodbot.messaging.helper;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import org.junit.jupiter.api.Test;

public class HashHelperTest
{
	@Test
	public void shouldGenerateValidHashValues()
	{
		final List<String> testTimestamps = Arrays.asList(
			"1258825312",
			"1257961312",
			"1258220512",
			"1258911712",
			"1258220551");

		final List<String> results = new ArrayList<>();
		for (String testTimestamp : testTimestamps)
		{
			results.add(HashHelper.getMac256Hash(testTimestamp));
		}

		assertThat(results).containsExactly(
			"fdc008b435f08d529ca7db6f666a21e4",
			"349bcb22f631170f220e6a2885729fa6",
			"17f9c16b34b8340ac4d34630b991f552",
			"00bc3b8015c93b23aea6796e48edd041",
			"1fe62d8cfebdbf30b0709a1a885fbdae");
	}
}
