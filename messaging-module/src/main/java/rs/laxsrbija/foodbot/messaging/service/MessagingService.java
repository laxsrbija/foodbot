package rs.laxsrbija.foodbot.messaging.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.configuration.MessagingServiceConfiguration;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingService
{
	private static final String PYTHON_COMPILER = "python3";
	private static final int SUCCESS = 0;

	private final MessagingServiceConfiguration _messagingServiceConfiguration;

	public void sendMessage(final String message)
	{
		try
		{
			callPythonScript(
				_messagingServiceConfiguration.getScriptName(),
				_messagingServiceConfiguration.getScriptPath(),
				_messagingServiceConfiguration.getUsername(),
				_messagingServiceConfiguration.getPassword(),
				_messagingServiceConfiguration.getChatId(),
				message);
		}
		catch (final InterruptedException | IOException e)
		{
			log.error("Unable to send a message: " + e.getMessage(), e);
		}
	}

	private void callPythonScript(
		final String scriptName,
		final String scriptPath,
		final String username,
		final String password,
		final String chatId,
		final String message)
		throws InterruptedException, IOException
	{
		final File workingDirectory = new File(scriptPath);
		final ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_COMPILER, scriptName, username, password, chatId, message);
		processBuilder.directory(workingDirectory);

		final Process process = processBuilder.start();
		process.waitFor();

		if (process.exitValue() != SUCCESS)
		{
			log.error(IOUtils.toString(process.getErrorStream(), Charset.defaultCharset()));
		}
	}
}
