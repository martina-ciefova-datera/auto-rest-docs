package com.test.autorestdocs.security.captcha;

import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/**
 * Service verifying recaptcha response from client side. Source:
 * https://www.devglan.com/angular/spring-boot-angular-captcha
 *
 * @author Martina Ciefova
 */
@Service
@Profile("captcha")
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaService {

    /**
     * Logger.
     *
     * @since 1.0.0
     */
    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(CaptchaService.class);

    /**
     * Client to perform requests.
     *
     * @since 1.0.0
     */
    private final WebClient webClient;

    /**
     * Recaptcha configuration.
     *
     * @since 1.0.0
     */
    private final CaptchaProperties properties;

    /**
     * Constructor.
     *
     * @param properties recaptcha configuration
     * @since 1.0.0
     */
    @Autowired
    public CaptchaService(CaptchaProperties properties) {
        this.webClient = WebClient.create();
        this.properties = properties;
    }

    /**
     * Verify recaptcha response.
     *
     * @param response recaptcha response
     * @return verification result
     * @since 1.0.0
     */
    public boolean verify(Optional<String> response) {
        LOGGER.info("Captcha verification started.");

        //we need https -> uri builder would remove slash after https
        StringBuilder uri = new StringBuilder()
                .append(properties.getVerifyUrl())
                .append("?")
                .append("secret=")
                .append(properties.getSecretKey())
                .append("&response");

        if(response.isPresent()){
            uri.append("=").append(response);
        }

        CaptchaResponse recaptchaResponse = null;
        try {
            LOGGER.debug("Captcha request sent.");
            recaptchaResponse
                    = this.webClient
                            .post()
                            .uri(uri.toString())
                            .retrieve()
                            .bodyToMono(CaptchaResponse.class)
                            .block();
        } catch (WebClientRequestException ex) {
            LOGGER.error("Captcha request seding failed.", ex);
            return false;
        }

        boolean result = recaptchaResponse.isSuccess();

        LOGGER.info("Captcha verification result: {}.", result);
        return result;
    }
}
