package com.test.autorestdocs.security.captcha;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Martina Ciefova
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCaptchaException extends RuntimeException {

    public InvalidCaptchaException() {
        super("Invalid Captcha.");
    }

    public InvalidCaptchaException(String message) {
        super(message);
    }

}
