package acme.security.ws;

import acme.security.component.ACMEContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Component
@Path("/guest")
public class GuestResource {

    private static final Logger logger = LoggerFactory.getLogger(GuestResource.class);

    @Context
    private ACMEContext context;

    @GET
    public String message() {
        logger.debug(String.format("Returning from Guest with logged user '%s' is admin? %s",
                context.getUsername(),
                context.isAdmin()));
        return "Guest";
    }
}
