package rs.laxsrbija.foodbot.messaging.helper;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import org.junit.Test;

public class HashHelperTest
{
	@Test
	public void shouldGenerateValidHashValues()
	{
		final List<String> testTimestamps = Arrays.asList(
			"1258825312");
		final List<String> results = new ArrayList<>();

		for (String testTimestamp : testTimestamps)
		{
			results.add(HashHelper.getMac256Hash(testTimestamp));
		}

		assertThat(results).containsExactly(
			"fdc008b435f08d529ca7db6f666a21e4");
	}
}
