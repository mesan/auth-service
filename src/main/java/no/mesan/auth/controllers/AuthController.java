package no.mesan.auth.controllers;

import no.mesan.auth.controllers.payload.AuthenticationRequestBody;
import no.mesan.auth.controllers.payload.AuthenticationTokenRequestBody;
import no.mesan.auth.controllers.results.AuthenticationTokenResult;
import no.mesan.auth.controllers.results.AuthenticationUserResult;
import no.mesan.auth.service.jwt.JwtService;
import no.mesan.auth.service.ldap.LdapAuthenticationResult;
import no.mesan.auth.service.ldap.LdapService;
import no.mesan.auth.service.ldap.LdapServiceException;
import no.mesan.auth.token.exceptions.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody final AuthenticationRequestBody payload) {
        try {
            final LdapAuthenticationResult result = ldapService.authenticate(payload.getEmail(), payload.getPassword());
            final String token = jwtService.generateToken(result.getEmail(), result.getExpirationDate());
            return ResponseEntity.ok(new AuthenticationTokenResult(token));
        } catch (final LdapServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping("/valid")
    public ResponseEntity<?> validate(@RequestBody final AuthenticationTokenRequestBody tokenRequestBody) {
        try {
            final String userId = jwtService.getUserIdFromToken(tokenRequestBody.getJwtToken());
            return ResponseEntity.ok(new AuthenticationUserResult(userId));
        } catch (final JwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
