package no.mesan.auth.service.jwt;

import no.mesan.auth.token.JWTTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    @Autowired
    private JWTTokenBuilder builder;

    public String generateToken(final String uniqueUserId, final Date expirationDate) {
        return builder.generateToken(uniqueUserId, expirationDate);
    }

    public void validate(final String compactJWT) {
        try {
            builder.extractUserId(compactJWT);
        } catch ()
    }
}
