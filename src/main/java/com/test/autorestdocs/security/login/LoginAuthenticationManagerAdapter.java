package com.test.autorestdocs.security.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;

/**
 *
 * @author Martina Ciefova
 */
public class LoginAuthenticationManagerAdapter
        extends ReactiveAuthenticationManagerAdapter
        implements LoginAuthenticationManager {

    public LoginAuthenticationManagerAdapter(
            AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

}
