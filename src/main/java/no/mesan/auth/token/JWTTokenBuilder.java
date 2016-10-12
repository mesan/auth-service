package no.mesan.auth.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTTokenBuilder {

    private final String secret;

    public JWTTokenBuilder(final String secret) {
        this.secret = secret;
    }

    public String generateToken(final String uniqueUserId, final Date expirationDate) {
        return Jwts.builder()
                .setSubject(uniqueUserId)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractUserId(final String compactJWT) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(compactJWT)
                .getBody()
                .getSubject();
    }
}
