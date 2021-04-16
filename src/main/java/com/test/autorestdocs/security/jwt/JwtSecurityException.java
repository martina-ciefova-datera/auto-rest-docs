package com.test.autorestdocs.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Martina Ciefova
 */
public class JwtSecurityException extends AuthenticationException {

    public JwtSecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtSecurityException(String msg) {
        super(msg);
    }

}
