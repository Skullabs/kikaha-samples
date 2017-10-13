package kikaha.samples.websockets.security;

import io.undertow.Undertow;
import kikaha.core.DeploymentContext;
import kikaha.core.modules.Module;
import kikaha.core.util.SystemResource;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Singleton
public class DatabaseInitialPopulation implements Module {

    @Inject DataSource dataSource;

    @Override
    public void load(Undertow.Builder builder, DeploymentContext deploymentContext) throws IOException {
        log.info( "Creating users and roles..." );
        val script = SystemResource.readFileAsString( "tables.sql", "UTF-8" );
        val jdbi = Jdbi.create( dataSource );
        try ( val handle = jdbi.open() ) {
            handle.useTransaction( h -> h.createScript( script ).execute() );
        }
    }
}
