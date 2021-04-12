package com.test.autorestdocs.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Martina Ciefova
 */
@ConstructorBinding
@ConfigurationProperties("jwt")
@Validated
public class JwtProperties {

    @NotBlank
    private String issuer;

    @NotNull
    private Long expiration;

    @NotBlank
    private String algorithmCode;

    private SignatureAlgorithm algorithm;

    private Key secret;

    public JwtProperties(String issuer, Long expiration,
                         @Name("algorithm") String algorithmCode) {
        this.issuer = issuer;
        this.expiration = expiration;
        this.algorithmCode = algorithmCode;
        this.algorithm = SignatureAlgorithm.forName(algorithmCode);
        this.secret = Keys.secretKeyFor(algorithm);
    }

    public String getIssuer() {
        return issuer;
    }

    public Long getExpiration() {
        return expiration;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public Key getSecret() {
        return secret;
    }


}
