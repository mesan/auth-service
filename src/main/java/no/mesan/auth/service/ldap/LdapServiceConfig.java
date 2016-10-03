package no.mesan.auth.service.ldap;

/**
 * Holds connection info for LDAP Azure connections.
 */
public class LdapServiceConfig {

    private final String authority;
    private final String resource;
    private final String clientId;

    public LdapServiceConfig(String authority, String resource, String clientId) {
        this.authority = authority;
        this.resource = resource;
        this.clientId = clientId;
    }

    public String getAuthority() {
        return authority;
    }

    public String getResource() {
        return resource;
    }

    public String getClientId() {
        return clientId;
    }
}
