package com.test.autorestdocs.security.login;

import com.test.autorestdocs.security.captcha.CaptchaService;
import com.test.autorestdocs.security.captcha.InvalidCaptchaException;
import com.test.autorestdocs.security.jwt.JwtResponse;
import com.test.autorestdocs.security.jwt.JwtTokenCreator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final LoginAuthenticationManager authentication;

    private final Optional<CaptchaService> captchaService;

    private final JwtTokenCreator tokenCreator;

    @Autowired
    public LoginServiceImpl(LoginAuthenticationManager authentication,
                            Optional<CaptchaService> captchaService,
                            JwtTokenCreator tokenCreator) {
        this.authentication = authentication;
        this.captchaService = captchaService;
        this.tokenCreator = tokenCreator;
    }

    @Override
    public Mono<ResponseEntity> login(String captcha, LoginRequest credentials) {
        return Mono.just(credentials)
                .map(cr
                        -> new UsernamePasswordAuthenticationToken(
                        cr.getUsername(),
                        cr.getPassword()))
                .flatMap(auth -> authentication.authenticate(auth))
                .switchIfEmpty(
                        Mono.error(new BadCredentialsException("Bad Credentials")))
                .zipWith(Mono.just(Optional.ofNullable(captcha)))
                .filter(tuple
                        -> captchaService.isEmpty() ? true
                : captchaService.get().verify(tuple.getT2()))
                .switchIfEmpty(Mono.error(InvalidCaptchaException::new))
                .map(tuple
                        -> (UserDetails) tuple.getT1().getPrincipal())
                .map(user
                        -> ResponseEntity.ok(
                        new JwtResponse(tokenCreator.generateToken(user))));
    }
}
