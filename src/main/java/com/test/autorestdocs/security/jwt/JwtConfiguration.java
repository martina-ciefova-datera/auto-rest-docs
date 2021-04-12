package com.test.autorestdocs.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

/**
 *
 * @author Martina Ciefova
 */
@Configuration
@Profile("jwt")
public class JwtConfiguration {

    @Bean
    public AuthenticationWebFilter
            jwtWebFilter(JwtTokenCreator creator,
                         JwtAuthenticationManager manager,
                         JwtAuthenticationConverter converter) {
        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(manager);
        jwtAuthFilter.setServerAuthenticationConverter(converter);
        jwtAuthFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/test", "/test/**"));
        return jwtAuthFilter;
    }
}
