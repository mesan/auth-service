package no.mesan.auth.config;

import no.mesan.auth.service.ldap.LdapServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LdapConfig {

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
