package acme.security.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/guest")
public class GuestResource {

    private static final Logger logger = LoggerFactory.getLogger(GuestResource.class);

    @GET
    public String message() {
        logger.debug(String.format("Returning from Guest"));
        return "Guest";
    }
}
