package acme.security.config;

import acme.security.component.ACMEContext;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 * Created by cristianosanchez on 16/06/16.
 */
@RequestScoped
@Provider
public class ACMEContextFactory implements Factory<ACMEContext> {

	@Context
	protected SecurityContext securityContext;

	@Override
	public ACMEContext provide() {
		if (securityContext == null) {
			throw new IllegalStateException("Can't inject security context - security context is null.");

		} else {
			ACMEContext acmeContext =
					new ACMEContext(securityContext.getUserPrincipal().getName(), securityContext.isUserInRole("ADMIN"));
			return acmeContext;
		}
	}

	@Override
	public void dispose(ACMEContext acmeContext) {

	}
}
