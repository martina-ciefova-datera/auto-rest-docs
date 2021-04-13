package com.test.autorestdocs.security;

import java.util.Collections;
import static org.springframework.core.ResolvableType.forClass;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Component
public class LoginAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange swe) {
        return swe.getRequest()
                .getBody()
                .map(Flux::just)
                .map(body -> {
                    return new Jackson2JsonDecoder()
                            .decodeToMono(body, forClass(LoginRequest.class),
                                          MediaType.APPLICATION_JSON,
                                          Collections.emptyMap())
                            .cast(LoginRequest.class);
                }).next()
                .flatMap(x -> x)
                .map(login -> new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword()));
    }

}
