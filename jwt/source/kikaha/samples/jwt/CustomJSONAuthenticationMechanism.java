package kikaha.samples.jwt;

import java.util.*;
import javax.inject.Inject;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.undertow.security.idm.Account;
import io.undertow.server.HttpServerExchange;
import kikaha.core.modules.security.Session;
import kikaha.core.modules.undertow.BodyResponseSender;
import kikaha.urouting.serializers.jackson.*;

/**
 *
 */
public class CustomJSONAuthenticationMechanism extends JSONAuthenticationMechanism {

	@Inject Jackson jackson;
	@Inject JwtTokenFactory tokenFactory;

	@Override
	public boolean sendAuthenticationSuccess(HttpServerExchange exchange, Session session) {
		try {
			final Account account = session.getAuthenticatedAccount();
			final JwtToken
				accessToken = tokenFactory.createAccessJwtToken(account),
				refreshToken = tokenFactory.createRefreshToken(account);

			final Map<String, String> tokenMap = new HashMap<>();
			tokenMap.put("token", accessToken.getToken());
			tokenMap.put("refreshToken", refreshToken.getToken());

			final String response = jackson.objectMapper().writeValueAsString(tokenMap);
			BodyResponseSender.response( exchange, 200, "application/json", response );

			return true;
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}
}
