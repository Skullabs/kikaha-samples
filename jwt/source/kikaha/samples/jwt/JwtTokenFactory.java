package kikaha.samples.jwt;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;
import io.jsonwebtoken.*;
import io.undertow.security.idm.*;
import kikaha.core.modules.security.FixedUsernameAndRolesAccount;

/**
 * Heavily inspired on Vladmir Stankovic's class with same name, this class
 * intent to be a centralized place to generate tokens.
 */
public class JwtTokenFactory {

    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    @Inject JwtSettings settings;

    /**
     * Factory method for issuing new JWT Tokens.
     * @param account
     */
    public JwtToken createAccessJwtToken(Account account) {
        final String userName = account.getPrincipal().getName();
        ensureUserNameIsValid( userName );

        final Set<String> roles = account.getRoles();
        ensureRolesAreValid( roles );

        final Claims claims = Jwts.claims().setSubject( userName );
        claims.put("scopes", roles.stream().map( String::toString ).collect(Collectors.toList()));

        final String token = generateToken(claims, settings.getTokenExpirationTime()).compact();
        return new JwtToken(token, claims);
    }

    public JwtToken createRefreshToken(Account account) {
        final String userName = account.getPrincipal().getName();
        ensureUserNameIsValid( userName );

        final Claims claims = Jwts.claims().setSubject( userName );
        claims.put("scopes", Collections.singletonList(REFRESH_TOKEN));

        final String token = generateToken( claims, settings.getRefreshTokenExpTime() )
          .setId(UUID.randomUUID().toString())
          .compact();

        return new JwtToken(token, claims);
    }

    JwtBuilder generateToken(final Claims claims, int expirationTimeInMinutes ) {
        final LocalDateTime now = now();
        final LocalDateTime soon = now.plusMinutes( expirationTimeInMinutes );

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(settings.getTokenIssuer())
            .setIssuedAt( toDate(now) )
            .setExpiration( toDate(soon) )
            .signWith( SignatureAlgorithm.HS512, settings.getTokenSigningKey() );
    }

    public AccountAndCredential createAccountFromClaims( Jws<Claims> claims ) {
        final List<String> rolesAsList = claims.getBody().get("scopes", List.class);
        final Set<String> roles = new HashSet<>();
        roles.addAll(rolesAsList);

        final String username = claims.getBody().getSubject();
        return new AccountAndCredential(username, roles);
    }

    static void ensureUserNameIsValid( String userName ) {
        if ( userName == null || userName.isEmpty() )
            throw new IllegalArgumentException("Cannot create JWT Token without username");
    }

    static void ensureRolesAreValid( Set<String> roles ){
        if (roles == null || roles.isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");
    }

    // XXX: boring Java8 Date-Time API
    static Date toDate( final LocalDateTime localDateTime ) {
        final Instant instant = localDateTime.atZone( ZoneId.systemDefault() ).toInstant();
        return Date.from( instant );
    }

    static LocalDateTime now(){
        final Date currentDate = new Date();
        return currentDate.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }

    /**
     * An {@link Account} that behaves as {@link Credential}. It is useful to check if the username
     * is valid.
     */
    public static class AccountAndCredential extends FixedUsernameAndRolesAccount implements Credential
    {
        public AccountAndCredential( String username, Set<String> roles ) {
            super( username, roles );
        }
    }
}