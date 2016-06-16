package acme.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * SpringBootApplication includes the follow annotation:
 * <p>
 * - Configuration
 * - EnableAutoConfiguration
 * - ComponentScan
 * <p>
 * One can override an annotation to setup desired values.
 *
 * Extending SpringBootServletInitializer makes app enabled to execute as
 * deployable WAR and embedded container as well.
 */

@SpringBootApplication
public class App extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Iniciando Security POC");
        SpringApplication.run(App.class, args);
    }
}