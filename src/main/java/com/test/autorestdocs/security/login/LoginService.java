package com.test.autorestdocs.security.login;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
public interface LoginService {

    public Mono<ResponseEntity> login(String captcha, LoginRequest credentials);
}
