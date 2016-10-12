package no.mesan.auth.service.ldap;

import no.mesan.auth.config.LdapConfig;
import no.mesan.auth.config.LdapTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Run with -Dldap.it.test.username=<MyMesanOutlookEmail> -Dtest.ldap.it.test.password=<MyMesanOutlookPassword>
 */
@ContextConfiguration(classes = {LdapTestConfig.class, LdapService.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class LdapServiceIT {

    @Autowired
    private LdapService ldapService;
    @Value("${ldap.it.test.username}")
    private String testEmail;
    @Value("${ldap.it.test.password}")
    private String testPassword;

    @Before
    public void setUp() throws Exception {
        if (testEmail == null || testPassword == null) {
            throw new RuntimeException("email and password for integration testing LDAP-service cannot be null");
        }
    }

    @Test
    public void authenticateSuccessfulWithValidUsernameAndPassword() throws Exception {
        LdapServiceResult result = ldapService.authenticate(testEmail, testPassword);
        assertNotNull(result.getExpirationDate());
        assertNotNull(result.getAccessToken());

        assertNotNull(result.getUserInfo().getUniqueId());
        assertNotNull(result.getUserInfo().getDisplayableId());
        assertNotNull(result.getUserInfo().getFamilyName());
    }
}
