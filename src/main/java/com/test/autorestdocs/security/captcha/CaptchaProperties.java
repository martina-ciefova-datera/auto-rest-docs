package com.test.autorestdocs.security.captcha;

import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Martina Ciefova
 */
@ConstructorBinding
@ConfigurationProperties("captcha")
@Validated
public class CaptchaProperties {
    public static final String CAPTCHA_HEADER_NAME = "recaptcha-response";

    @NotBlank
    private final String verifyUrl;

    @NotBlank
    private final String secretKey;

    public CaptchaProperties(String verifyUrl, String secretKey) {
        this.verifyUrl = verifyUrl;
        this.secretKey = secretKey;
    }

    public String getVerifyUrl() {
        return verifyUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

}
