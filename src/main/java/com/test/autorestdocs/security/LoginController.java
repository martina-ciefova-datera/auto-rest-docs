package com.test.autorestdocs.security;

import com.test.autorestdocs.security.jwt.JwtResponse;
import com.test.autorestdocs.security.jwt.JwtTokenCreator;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@RestController
public class LoginController {

    private final JwtTokenCreator tokenCreator;

    @Autowired
    public LoginController(JwtTokenCreator tokenCreator) {
        this.tokenCreator = tokenCreator;
    }

    /**
     * Login endpoint.
     *
     * @param principal user
     * @return login message
     */
    @GetMapping("/login")
    public Mono<ResponseEntity> greet(Mono<Principal> principal) {

        return principal
                .map(user -> ((Authentication) user).getPrincipal())
                .map(user -> (UserDetails) user)
                .map(user
                        -> ResponseEntity.ok(
                        new JwtResponse(tokenCreator.generateToken(user))));
    }
}
