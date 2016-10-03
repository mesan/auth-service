package no.mesan.auth.service.ldap;

import no.mesan.auth.exceptions.ApplicationException;

public class LdapServiceException extends ApplicationException {

    public LdapServiceException(String message) {
        super(message);
    }

    public LdapServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LdapServiceException(Throwable cause) {
        super(cause);
    }
}
