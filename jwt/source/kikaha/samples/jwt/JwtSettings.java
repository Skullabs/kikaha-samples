package kikaha.samples.jwt;

import javax.inject.*;
import io.jsonwebtoken.*;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import kikaha.config.Config;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Singleton
public class JwtSettings {

	static String HEADER_PREFIX = "Bearer ";
	static HttpString X_AUTHORIZATION = new HttpString("X-Authorization");

	@Inject Config config;

	/**
	 * Token issuer.
	 */
	@Getter(lazy = true)
	private final String tokenIssuer = config.getString( "server.jwt.key-issuer" );

	/**
	 * Key is used to sign {@link JwtToken}.
	 */
	@Getter(lazy = true)
	private final String tokenSigningKey = config.getString( "server.jwt.singing-key" );

	/**
	 * {@link JwtToken} will expire after this time.
	 */
	@Getter(lazy = true)
	private final int tokenExpirationTime = config.getInteger( "server.jwt.token-expiration-time" );

	/**
	 * {@link JwtToken} can be refreshed during this timeframe.
	 */
	@Getter(lazy = true)
	private final int refreshTokenExpTime = config.getInteger( "server.jwt.refresh-token-expiration-time" );

	/**
	 * Extract the {@link Claims} from a given request ({@code exchange}).
	 *
	 * @param exchange
	 * @return
	 */
	public Jws<Claims> extractToken( HttpServerExchange exchange ){
		final String authorization = exchange.getRequestHeaders().getFirst(X_AUTHORIZATION);
		final String token = authorization.substring(HEADER_PREFIX.length());
		return parse( getTokenSigningKey(), token );
	}

	static Jws<Claims> parse(String signingKey, String token) {
		try {
			return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException cause) {
			log.error("Invalid JWT Token", cause);
			throw new Exceptions.BadCredentialsException("Invalid JWT token: ", cause);
		} catch (ExpiredJwtException cause) {
			log.info("JWT Token is expired", cause);
			throw new Exceptions.JwtExpiredTokenException("JWT Token expired", cause);
		}
	}
}
