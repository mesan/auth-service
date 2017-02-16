package no.mesan.auth.controllers.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationTokenResult {

    @JsonProperty(value = "token")
    private final String jwtToken;

    public AuthenticationTokenResult(final String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    @Override
    public String toString() {
        return "AuthenticationTokenResult{" +
                "jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
