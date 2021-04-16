package com.test.autorestdocs.security.jwt;

import com.test.autorestdocs.security.jwt.blacklist.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
public class JwtTokenValidator {

    private final BlacklistRepository blacklist;

    private final JwtTokenParser parser;

    @Autowired
    public JwtTokenValidator(BlacklistRepository blacklist,
                             JwtTokenParser parser) {
        this.blacklist = blacklist;
        this.parser = parser;
    }

    public boolean tokenValid(String token) {
        return !blacklist.existsByToken(token) && !parser.checkTokenExpired(token);
    }

}
