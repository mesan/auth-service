package no.mesan.auth.token.exceptions;

import no.mesan.auth.exceptions.ApplicationException;

public class JwtException extends ApplicationException {
    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(Throwable cause) {
        super(cause);
    }
}
