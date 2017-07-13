package sample;

import javax.inject.Singleton;
import kikaha.urouting.api.*;
import lombok.*;

/**
 *
 */
@Singleton
public class HelloResource {

	@GET
	@Path("hello/{name}")
	public Hello sayHello(@PathParam("name") String name) {
		return new Hello( name );
	}

	@Getter
	@RequiredArgsConstructor
	public static class Hello {
		final String name;
	}
}
