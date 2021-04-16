package com.test.autorestdocs.security;

import com.test.autorestdocs.security.jwt.JwtAuthenticationConverter;
import com.test.autorestdocs.security.jwt.JwtTokenParser;
import com.test.autorestdocs.security.jwt.blacklist.Blacklist;
import com.test.autorestdocs.security.jwt.blacklist.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
public class LogoutSuccessHandler implements ServerLogoutSuccessHandler {

    private final BlacklistRepository repository;

    private final JwtTokenParser parser;

    private final JwtAuthenticationConverter converter;

    @Autowired
    public LogoutSuccessHandler(BlacklistRepository repository,
                                JwtTokenParser parser,
                                JwtAuthenticationConverter converter) {
        this.repository = repository;
        this.parser = parser;
        this.converter = converter;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange wfe, Authentication a) {
        String token = converter.parseToken(wfe.getExchange()).get();
        Blacklist blackList = new Blacklist();
        blackList.setToken(token);
        blackList.setExpirationDate(parser.parseExpiration(token));

        repository.save(blackList);

        return Mono.empty();
    }

}
