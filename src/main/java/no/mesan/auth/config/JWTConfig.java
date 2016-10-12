package no.mesan.auth.config;

import no.mesan.auth.token.JWTTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JWTConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JWTTokenBuilder jwtTokenBuilder() {
        final String secret = environment.getRequiredProperty("jwt.secret");
        return new JWTTokenBuilder(secret);
    }
}
