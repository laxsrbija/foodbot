package rs.laxsrbija.foodbot.messaging.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.laxsrbija.foodbot.messaging.helper.TokenLifetimeHelper;
import rs.laxsrbija.foodbot.messaging.model.*;
import rs.laxsrbija.foodbot.messaging.provider.*;
import rs.laxsrbija.foodbot.messaging.repository.RegistrationTokenRepository;
import rs.laxsrbija.foodbot.messaging.repository.SkypeTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkypeTokenService
{
	private final LoginTokenProvider _loginTokenProvider;
	private final SkypeTokenProvider _skypeTokenProvider;
	private final RegistrationTokenProvider _registrationTokenProvider;
	private final SkypeTokenRepository _skypeTokenRepository;
	private final RegistrationTokenRepository _registrationTokenRepository;

	/**
	 * Main service for requesting registration tokens.
	 * It returns a cached registration token, if it's still valid.
	 * Otherwise, it requests a new token and saves it in a local cache.
	 */
	RegistrationToken getValidRegistrationToken()
	{
		final Optional<RegistrationToken> token = _registrationTokenRepository.getData();

		if (token.isPresent() && !TokenLifetimeHelper.tokenHasExpired(token.get()))
		{
			log.info("The cached registration token is still valid, reusing it");
			return token.get();
		}

		final RegistrationToken registrationToken = renewRegistrationToken();
		log.info("Successfully obtained a new registration token");
		return registrationToken;
	}

	private RegistrationToken renewRegistrationToken()
	{
		log.info("The registration token has expired, obtaining a new one...");

		final Optional<SkypeToken> token = _skypeTokenRepository.getData();

		final SkypeToken skypeToken;
		if (token.isEmpty() || TokenLifetimeHelper.tokenHasExpired(token.get()))
		{
			skypeToken = renewSkypeToken();
		}
		else
		{
			log.info("The cached Skype token is still valid, reusing it to obtain a registration token...");
			skypeToken = token.get();
		}

		log.info("Obtaining a registration token...");
		final RegistrationToken registrationToken = _registrationTokenProvider.getRegistrationToken(skypeToken);
		return _registrationTokenRepository.saveData(registrationToken);
	}

	private SkypeToken renewSkypeToken()
	{
		log.info("The Skype token has expired, obtaining a new one...");

		log.info("Obtaining login parameters...");
		final LoginParameters loginParameters = LoginParametersProvider.getLoginParameters();

		log.info("Obtaining a login token...");
		final String loginToken = _loginTokenProvider.getLoginToken(loginParameters);

		log.info("Obtaining a Skype token...");
		final SkypeToken skypeToken = _skypeTokenProvider.getSkypeToken(loginToken);
		return _skypeTokenRepository.saveData(skypeToken);
	}
}
