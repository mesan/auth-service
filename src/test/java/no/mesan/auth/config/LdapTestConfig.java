package no.mesan.auth.config;

import no.mesan.auth.service.ldap.LdapServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * We have to write this duplicate test-specific implementation in order to load application-test.properties
 * as a resource which we extract values from. Alternative is adding
 * @PropertySource("classpath:application-test.properties") to our LdapConfig class.
 */
@Configuration
@PropertySource("classpath:application-test.properties")
public class LdapTestConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LdapServiceConfig ldapServiceConfig() {
        final String authority = environment.getRequiredProperty("ldap.authority");
        final String resource = environment.getRequiredProperty("ldap.resource");
        final String clientId = environment.getRequiredProperty("ldap.client-id");
        return new LdapServiceConfig(authority, resource, clientId);
    }
}
