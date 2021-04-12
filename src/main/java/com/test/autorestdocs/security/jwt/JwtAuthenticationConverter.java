package com.test.autorestdocs.security.jwt;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    public static final String PREFIX = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();

        String authorization
                = request.getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.startsWithIgnoreCase(authorization, PREFIX)) {
            return Mono.empty();
        }

        String authorizationToken = authorization.substring(PREFIX.length());

        return Mono.just(new UsernamePasswordAuthenticationToken(authorizationToken, authorizationToken));
    }
}
