package com.test.autorestdocs.security.login;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Martina Ciefova
 */
public class LoginRequest implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
