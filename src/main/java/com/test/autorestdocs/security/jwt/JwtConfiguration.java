package com.test.autorestdocs.security.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 *
 * @author Martina Ciefova
 */
@Configuration
@Profile("jwt")
public class JwtConfiguration {

//    @Bean
    public AuthenticationWebFilter
            jwtWebFilter(JwtAuthenticationManager manager,
                         JwtAuthenticationConverter converter) {
        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(manager);
        jwtAuthFilter.setServerAuthenticationConverter(converter);
        return jwtAuthFilter;
    }
}
