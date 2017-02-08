package no.mesan.auth.controllers;

import no.mesan.auth.controllers.payload.AuthenticationRequestBody;
import no.mesan.auth.service.ldap.LdapService;
import no.mesan.auth.service.ldap.LdapServiceException;
import no.mesan.auth.service.ldap.LdapAuthenticationResult;
import no.mesan.auth.token.JWTTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private LdapService ldapService;
    @Autowired
    private JWTTokenBuilder jwtTokenBuilder;

    @PostMapping("")
    public ResponseEntity<String> authenticate(@RequestBody final AuthenticationRequestBody payload) {
        try {
            final LdapAuthenticationResult result = ldapService.authenticate(payload.getEmail(), payload.getPassword());
            final String token = jwtTokenBuilder.generateToken(result.getUserId(), result.getExpirationDate());
            return ResponseEntity.ok(token);
        } catch (LdapServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody final String token) {
        try {
            jwtTokenBuilder.validate(token);
        } catch (RuntimeException e) {

        }
    }
}
