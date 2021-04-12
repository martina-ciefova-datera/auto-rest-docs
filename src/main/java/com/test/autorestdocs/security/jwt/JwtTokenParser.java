package com.test.autorestdocs.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("jwt")
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenParser {

    private final JwtProperties properties;

    @Autowired
    public JwtTokenParser(JwtProperties properties) {
        this.properties = properties;
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(properties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    protected String parseUserName(String token) {
        return parseClaims(token).getSubject();
    }

    protected Date parseExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    protected boolean checkTokenExpired(String token) {
        return parseExpiration(token).before(new Date());
    }

    protected List<GrantedAuthority> parseAuthorities(String token) {
        return Stream.ofNullable(
                parseClaims(token)
                        .get(JwtUserParser.ROLE, List.class))
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public UsernamePasswordAuthenticationToken
            parseUser(String token) {
        return new UsernamePasswordAuthenticationToken(
                parseUserName(token), null, parseAuthorities(token));
    }
}
