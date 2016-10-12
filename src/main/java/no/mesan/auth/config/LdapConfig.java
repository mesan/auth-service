package no.mesan.auth.config;

import no.mesan.auth.service.ldap.LdapServiceConfig;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class LdapConfig {

    @Value("${ldap.authority}")
    private String authority;
    @Value("${ldap.resource}")
    private String resource;
    @Value("${ldap.client-id}")
    private String clientId;

    @Bean
    public LdapServiceConfig ldapServiceConfig() {
        return new LdapServiceConfig(authority, resource, clientId);
    }
}
