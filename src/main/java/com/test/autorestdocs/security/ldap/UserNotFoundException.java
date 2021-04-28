package com.test.autorestdocs.security.ldap;

/**
 *
 * @author Martina Ciefova
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found.");
    }


    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
