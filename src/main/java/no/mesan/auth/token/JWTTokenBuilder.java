package no.mesan.auth.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import no.mesan.auth.domain.User;
import no.mesan.auth.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Value;

public class JWTTokenBuilder {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(final User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                // TODO
                //.setExpiration()
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void parseToen(final String compactJwt) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(compactJwt);
        } catch (SignatureException e) {
            throw new ApplicationException("Failed to parse token, cannot trust this token!");
        }
    }
}
