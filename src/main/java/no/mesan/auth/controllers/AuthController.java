package no.mesan.auth.controllers;

import com.microsoft.aad.adal4j.AuthenticationResult;
import no.mesan.auth.controllers.payload.AuthenticationRequestBody;
import no.mesan.auth.service.ldap.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private LdapService ldapService;

    @PostMapping("")
    public String authenticate(@RequestBody final AuthenticationRequestBody payload) {
        System.out.println(payload);
        final AuthenticationResult result = ldapService.authenticate(payload.getEmail(), payload.getPassword());
        System.out.println(result.getUserInfo().getUniqueId());
        System.out.println(result.getUserInfo().getDisplayableId());
        System.out.println(result.getUserInfo().getFamilyName());
        System.out.println(result.getUserInfo().getGivenName());
        return null;
    }
}
