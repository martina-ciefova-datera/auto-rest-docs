package com.test.autorestdocs.security.captcha;

import static com.test.autorestdocs.security.captcha.CaptchaProperties.CAPTCHA_HEADER_NAME;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("captcha")
public class CaptchaAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final CaptchaService captcha;

    @Autowired
    public CaptchaAuthenticationSuccessHandler(CaptchaService captcha) {
        this.captcha = captcha;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange wfe,
                                              Authentication a) {
        ServerWebExchange exchange = wfe.getExchange();

        HttpHeaders headers
                = exchange.getRequest().getHeaders();

        Optional<String> captchaHeader
                = Optional.ofNullable(headers.getFirst(CAPTCHA_HEADER_NAME));

        if (!captcha.verify(captchaHeader)) {
            return Mono.error(new InvalidCaptchaException());
        }

        return wfe.getChain().filter(exchange);
    }

}
