package acme.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@Profile("local")
public class InMemorySecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(InMemorySecurityConfiguration.class);

    @Autowired
    private Environment environment;

    /**
     * This section defines the user accounts which can be used for
     * authentication as well as the roles each user has.
     *
     * Note that using .roles("GUEST") it's the same as .hasAuthority("ROLE_GUEST").
     * When using roles, prefix _ROLE it's automatically added.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.warn("Running EMBEDDED CONTAINER - configuring AuthenticationManagerBuilder");
        auth.inMemoryAuthentication()
                .withUser("admin1").password("admin@123").roles("GUEST", "ADMIN")
                .and()
                .withUser("user1").password("user@123").roles("GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.warn("Running EMBEDDED CONTAINER - configuring HttpSecurity");
        http.authorizeRequests()
            .anyRequest().fullyAuthenticated()
            .and().httpBasic();
    }

    private boolean isProfile(String profileName) {
        String [] profiles = environment.getActiveProfiles();
        //return Stream.of(profiles).anyMatch(p -> p.equals(profileName));
        return Arrays.asList(profiles).contains(profileName);
    }
}