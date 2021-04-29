package com.test.autorestdocs.security.jwt.blacklist.clean;

/**
 *
 * @author Martina Ciefova
 */
public class BlacklistCleanerProperties {

    private final String schedule;

    public BlacklistCleanerProperties(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule() {
        return schedule;
    }

}
