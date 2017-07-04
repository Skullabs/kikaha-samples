package kikaha.samples.jwt;

import javax.inject.Singleton;
import kikaha.urouting.api.*;

/**
 *
 */
public interface Exceptions {

	class BadCredentialsException extends RuntimeException {
		public BadCredentialsException( String message, Throwable cause ) {
			super(message,cause);
		}
		public BadCredentialsException( String message ){
			super(message);
		}
	}

	class JwtExpiredTokenException extends RuntimeException {
		public JwtExpiredTokenException ( String message, Throwable cause ) {
			super(message,cause);
		}
	}

	@Singleton
	class BadCredentialsExceptionHandler implements ExceptionHandler<BadCredentialsException> {
		@Override
		public Response handle(BadCredentialsException exception) {
			return DefaultResponse.badRequest()
					.entity( exception.getMessage() );
		}
	}
	
	@Singleton
	class JwtExpiredTokenExceptionHandler implements ExceptionHandler<JwtExpiredTokenException> {
		@Override
		public Response handle(JwtExpiredTokenException exception) {
			return DefaultResponse.badRequest()
					.entity( exception.getMessage() );
		}
	}
}
