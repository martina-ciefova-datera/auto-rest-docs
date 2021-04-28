package com.test.autorestdocs.security.jwt;

import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
public class JwtTokenCreator {

    private final JwtProperties properties;

    private final JwtUserParser userParser;

    @Autowired
    public JwtTokenCreator(JwtProperties properties,
                           JwtUserParser userParser) {
        this.properties = properties;
        this.userParser = userParser;
    }

    public String generateToken(UserDetails user) {
        Instant creationTime = Instant.now();
        Instant expiration
                = creationTime
                        .plusSeconds(properties.getExpiration());

        return Jwts.builder()
                .setIssuer(properties.getIssuer())
                .setSubject(user.getUsername())
//                .addClaims(userParser.parseClaims(user))
                .setIssuedAt(Date.from(creationTime))
                .setExpiration(Date.from(expiration))
                .signWith(properties.getSecret(), properties.getAlgorithm())
                .compact();
    }

}
