package com.test.autorestdocs.security.jwt;

import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(JwtAuthenticationManager.class);

    private final JwtTokenParser parser;

    private final JwtTokenValidator validator;

    @Autowired
    public JwtAuthenticationManager(JwtTokenParser parser,
                                    JwtTokenValidator validator) {
        this.parser = parser;
        this.validator = validator;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication a) {
        String token = a.getCredentials().toString();

        try {
            if (validator.tokenValid(token)) {
                return Mono.just(parser.parseUser(token));
            } else {
                return Mono.error(new JwtSecurityException("Invalid token"));
            }
        } catch (SignatureException ex) {
            LOGGER.error("Token parsing failed. {}", ex.getMessage());
            return Mono.error(new JwtSecurityException("Invalid token", ex));
        }
    }

}
