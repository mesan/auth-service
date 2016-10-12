package no.mesan.auth.token;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JWTTokenBuilderTest {

    private JWTTokenBuilder builder;
    private String secret;

    @Before
    public void setUp() throws Exception {
        secret = "Radiorespesjonens postkasse";
        builder = new JWTTokenBuilder(secret);
    }

    @Test(expected = SignatureException.class)
    public void nonMatchSignatureThrowsException() throws Exception {
        final String uniqueUserId = "4324-4342-3213-6665-0002";
        // Create token with expiration date 10 seconds in the past
        final Date expirationDate = Date.from(ZonedDateTime.now().minusSeconds(10).toInstant());

        final String unknownSecret = "unknown signature";

        assertNotEquals("secret used for building should not be the same as the secet used to build new invalid token",
                secret, unknownSecret);

        final String tokenSignedWithUnknownSecret = Jwts.builder()
                .setSubject(uniqueUserId)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, unknownSecret)
                .compact();

        builder.extractUserId(tokenSignedWithUnknownSecret);
    }

    @Test(expected = ExpiredJwtException.class)
    public void expiredTokenThrowsException() throws Exception {
        final String uniqueUserId = "4324-4342-3213-6665-0002";
        // Create token with expiration date 10 seconds in the past
        final Date expirationDate = Date.from(ZonedDateTime.now().minusSeconds(10).toInstant());

        final String expiredToken = builder.generateToken(uniqueUserId, expirationDate);

        builder.extractUserId(expiredToken);
    }

    @Test
    public void properBuildingOfToken() throws Exception {
        final String uniqueUserId = "4324-4342-3213-6665-0002";
        final Date expirationDateThreeDaysIntoTheFuture = Date.from(ZonedDateTime.now().plusDays(3).toInstant());
        final String compactJWT = builder.generateToken(uniqueUserId, expirationDateThreeDaysIntoTheFuture);

        // Validation throws exception if any errors occur
        final String extractedId = builder.extractUserId(compactJWT);

        assertEquals("User id should be identical when extracting with the same secret", uniqueUserId, extractedId);
    }
}
