package com.test.autorestdocs.security.captcha;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 *
 * @author Martina Ciefova
 */
public class CaptchaResponse {

    private boolean success;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "Europe/Prague")
    @JsonProperty("challenge_ts")
    private Date challengeTimestamp;

    private String hostname;

    @JsonProperty("error-codes")
    private String[] errorCodes;

    public CaptchaResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getChallengeTimestamp() {
        return challengeTimestamp;
    }

    public void setChallengeTimestamp(Date challengeTimestamp) {
        this.challengeTimestamp = challengeTimestamp;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String[] getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(String[] errorCodes) {
        this.errorCodes = errorCodes;
    }
}
