package no.mesan.auth.token;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TokenExpirationBuilder {

    @Value("${jwt.token-duration-in-seconds}")
    private Long tokenDurationInSeconds;

    public Date buildExpirationDateForToken(final Long currentMillis) {
        final Instant currentInstant = Instant.ofEpochMilli(currentMillis);
        final ZonedDateTime currentZoneDatetime = ZonedDateTime.ofInstant(currentInstant, ZoneId.systemDefault());
        final ZonedDateTime expirationDate = currentZoneDatetime.plusSeconds(tokenDurationInSeconds);
        return Date.from(expirationDate.toInstant());
    }
}
