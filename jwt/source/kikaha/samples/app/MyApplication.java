package kikaha.samples.app;

import javax.inject.Singleton;
import io.undertow.security.idm.Account;
import kikaha.core.modules.security.Session;
import kikaha.urouting.api.*;

/**
 *
 */
@Singleton
public class MyApplication {

	@GET
	@Path("api/me")
	public Account retrieveMySelf(@Context Session session) {
		return session.getAuthenticatedAccount();
	}
}
