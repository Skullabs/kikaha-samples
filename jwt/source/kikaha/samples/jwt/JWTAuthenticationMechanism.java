package kikaha.samples.jwt;

import javax.inject.*;
import io.jsonwebtoken.*;
import io.undertow.security.idm.Account;
import io.undertow.server.HttpServerExchange;
import kikaha.core.modules.security.*;
import kikaha.core.modules.undertow.BodyResponseSender;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Singleton
public class JWTAuthenticationMechanism implements AuthenticationMechanism {

	@Inject JwtSettings settings;
	@Inject JwtTokenFactory tokenFactory;

	@Override
	public Account authenticate(HttpServerExchange exchange, Iterable<IdentityManager> identityManagers, Session session)
	{
		final Jws<Claims> claims = settings.extractToken(exchange);
		final JwtTokenFactory.AccountAndCredential account = tokenFactory.createAccountFromClaims(claims);

		// intentionally skipping IdentityManager verification of username for the sake of simplicity.
		// you should verify if the username is valid in order to keep you app secure.

		return account;
	}

	@Override
	public boolean sendAuthenticationChallenge(HttpServerExchange exchange, Session session) {
		BodyResponseSender.response( exchange, 401, "plain/text", "Unauthorized" );
		return true;
	}
}
