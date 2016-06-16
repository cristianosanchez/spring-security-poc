package acme.security.config;

import acme.security.component.ACMEContext;
import acme.security.ws.AdminResource;
import acme.security.ws.GuestResource;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Configuration
public class JerseyConfiguration extends ResourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(JerseyConfiguration.class);

    private final static Class[] resources = new Class [] {
            GuestResource.class,
            AdminResource.class
    };

    public JerseyConfiguration () {
        StringBuilder logMsg = new StringBuilder("Configurando endpoints REST com Jersey API:\n");
        for (Class resourceClass : resources) {
            logMsg.append(String.format("\t%s\n", resourceClass.getCanonicalName()));
            register(resourceClass);
        }
        logger.info(logMsg.toString());

        logger.info("Configurando ROLES dinamicas para controle de autorização");
        register(RolesAllowedDynamicFeature.class);

        logger.info("Configurando ACMEContext for injection");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(ACMEContextFactory.class)
                        .to(ACMEContext.class)
                        .proxy(true)
                        .proxyForSameScope(false)
                        .in(RequestScoped.class);
            }
        });
    }
}