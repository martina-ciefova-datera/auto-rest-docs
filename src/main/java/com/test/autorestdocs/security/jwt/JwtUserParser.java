package com.test.autorestdocs.security.jwt;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
public class JwtUserParser {

    public static final String ROLE = "role";

    public Map<String, Object> parseClaims(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE, user.getAuthorities());

        return claims;
    }
}
