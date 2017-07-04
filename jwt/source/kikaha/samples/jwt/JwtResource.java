package kikaha.samples.jwt;

import static kikaha.samples.jwt.JwtTokenFactory.REFRESH_TOKEN;
import java.util.Set;
import javax.inject.*;
import io.jsonwebtoken.*;
import io.undertow.server.HttpServerExchange;
import kikaha.urouting.api.*;

/**
 *
 */
@Singleton
public class JwtResource {

	@Inject JwtSettings settings;
	@Inject JwtTokenFactory tokenFactory;

	@GET
	@Path( "api/token/create" )
	public JwtToken createToken(@Context HttpServerExchange exchange){
		final Jws<Claims> claims = settings.extractToken(exchange);
		final JwtTokenFactory.AccountAndCredential account = tokenFactory.createAccountFromClaims(claims);

		final Set<String> roles = account.getRoles();
		if ( roles == null || roles.isEmpty() || !roles.contains( REFRESH_TOKEN ) )
			throw new Exceptions.BadCredentialsException( "Bad refresh-token." );

		// intentionally skipping IdentityManager verification of username for the sake of simplicity.
		// you should verify if the username is valid in order to keep you app secure.

		return tokenFactory.createAccessJwtToken( account );
	}
}
