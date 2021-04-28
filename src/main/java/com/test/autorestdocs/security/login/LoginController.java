package com.test.autorestdocs.security.login;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@RestController
@Validated
public class LoginController {

    private final LoginService login;

    @Autowired
    public LoginController(LoginService login) {
        this.login = login;
    }

    /**
     * Login endpoint.
     *
     * @param captcha captcha header
     * @param credentials credentials
     * @return login message
     */
    @PostMapping("/login")
    public Mono<ResponseEntity>
            login(@RequestHeader(value = "recaptcha-response", required = false) String captcha,
                  @Valid @RequestBody LoginRequest credentials) {
        return login.login(captcha, credentials);
    }
}
