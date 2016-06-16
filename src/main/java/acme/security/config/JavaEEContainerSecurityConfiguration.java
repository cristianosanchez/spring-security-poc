package acme.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("wildfly")
public class JavaEEContainerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JavaEEContainerSecurityConfiguration.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.warn("Running on JAVA EE CONTAINER - configuring HttpSecurity");
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().jee().mappableRoles("GUEST","ADMIN");
    }
}