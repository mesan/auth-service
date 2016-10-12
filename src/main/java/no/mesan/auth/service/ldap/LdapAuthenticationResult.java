package no.mesan.auth.service.ldap;

import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.UserInfo;

import java.util.Date;

/**
 * Our wrapper for authentication result from LDAP
 */
public class LdapAuthenticationResult {

    private final AuthenticationResult authenticationResult;

    public LdapAuthenticationResult(AuthenticationResult authenticationResult) {
        this.authenticationResult = authenticationResult;
    }

    public String getAccessToken() {
        return authenticationResult.getAccessToken();
    }

    public Date getExpirationDate() {
        return authenticationResult.getExpiresOnDate();
    }

    public UserInfo getUserInfo() {
        return authenticationResult.getUserInfo();
    }
}
