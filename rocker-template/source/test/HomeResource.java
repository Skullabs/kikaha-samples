package test;

import javax.inject.Singleton;
import kikaha.rocker.RockerResponse;
import kikaha.urouting.api.*;

/**
 *
 */
@Singleton
public class HomeResource {

	@GET
	@Path( "/" )
	public Response renderHome(){
		return new RockerResponse()
			.templateName("views/index.rocker.html")
			.objects( "Rocker World!" );
	}
}
