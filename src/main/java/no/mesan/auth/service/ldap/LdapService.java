package no.mesan.auth.service.ldap;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Service-middleware for authenticating a user to LDAP
 */
@Component
public class LdapService {

    private final LdapServiceConfig config;

    @Autowired
    public LdapService(LdapServiceConfig config) {
        this.config = config;
    }

    /**
     * Authenitcate a given user based on email and password to LDAP
     *
     * @param email of the user to authenticate
     * @param password of the user to authenticate
     * @return the result of the authentication
     * @throws LdapServiceException if an error happens as a result of invalid connection or if the callback from
     * the service takes too long to respond
     */
    public AuthenticationResult authenticate(final String email, final String password) {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final AuthenticationContext context = new AuthenticationContext(config.getAuthority(), true, service);
            final Future<AuthenticationResult> resultFuture = context.acquireToken(config.getResource(),
                    config.getClientId(), email, password, null);
            return resultFuture.get();
        } catch (MalformedURLException e) {
            throw new LdapServiceException("Failed to authenticate to Azure LDAP due to URL", e);
        } catch (InterruptedException | ExecutionException e) {
            throw new LdapServiceException("Exception while waiting for response from LDAP", e);
        } finally {
            service.shutdown();
        }
    }
}
