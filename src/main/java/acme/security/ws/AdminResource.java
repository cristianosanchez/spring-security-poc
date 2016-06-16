package acme.security.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Component
@Path("/admin")
public class AdminResource {

    private static final Logger logger = LoggerFactory.getLogger(AdminResource.class);

    @GET
    @RolesAllowed("ADMIN")
    public String message() {
        logger.debug(String.format("Returning from Admin"));
        return "Admin";
    }

}
