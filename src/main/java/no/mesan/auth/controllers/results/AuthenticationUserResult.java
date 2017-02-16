package no.mesan.auth.controllers.results;

public class AuthenticationUserResult {

    private final String userId;

    public AuthenticationUserResult(final String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AuthenticationUserResult{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
