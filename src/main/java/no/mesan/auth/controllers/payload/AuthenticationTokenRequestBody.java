package no.mesan.auth.controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationTokenRequestBody {

    @JsonProperty(value = "token")
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString() {
        return "AuthenticationTokenResult{" +
                "jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
