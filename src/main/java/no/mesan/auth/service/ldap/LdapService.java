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
     * Authenticate a given user based on email and password to LDAP
     *
     * @param email of the user to authenticate
     * @param password of the user to authenticate
     * @return the result of the authentication
     * @throws LdapServiceException if an error happens as a result of invalid connection or if the callback from
     * the service takes too long to respond
     */
    public LdapServiceResult authenticate(final String email, final String password) {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final Future<AuthenticationResult> resultFuture = initiateRequest(service, config, email, password);
            return buildResult(resultFuture);

        } catch (InterruptedException | ExecutionException e) {
            throw new LdapServiceException("Exception while waiting for response from LDAP", e);
        } finally {
            service.shutdown();
        }
    }

    /**
     * Initiate the request to the authentication service with the corresponding application-config, and user credentials.
     *
     * @param service   executor execution the request
     * @param config    of the app which goes against LDAP authentication
     * @param email     of the user
     * @param password  of the user
     * @return the future result of the authentication request
     */
    private Future<AuthenticationResult> initiateRequest(final ExecutorService service, final LdapServiceConfig config,
                                                         final String email, final String password) {
        try {
            final AuthenticationContext context = new AuthenticationContext(config.getAuthority(), true, service);

            final Future<AuthenticationResult> authenticationResultFuture = context.acquireToken(config.getResource(),
                    config.getClientId(), email, password, null);

            return authenticationResultFuture;

        } catch (MalformedURLException e) {
            throw new LdapServiceException("Url for authentication was invalid", e);
        }
    }

    /**
     * Blocks for the result from an authentication request and returns the representation of the result.
     *
     * @param resultFuture future reqpresenting result of request to authentication service
     * @return the result of the authentication
     * @throws ExecutionException if blocking for the result throws exception
     * @throws InterruptedException if the blocking is interrupted
     */
    private LdapServiceResult buildResult(Future<AuthenticationResult> resultFuture) throws ExecutionException, InterruptedException {
        final AuthenticationResult authenticationResult = resultFuture.get();
        return new LdapServiceResult(authenticationResult);
    }
}
