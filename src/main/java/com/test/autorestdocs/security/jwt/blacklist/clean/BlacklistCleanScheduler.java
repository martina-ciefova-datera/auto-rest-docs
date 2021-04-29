package com.test.autorestdocs.security.jwt.blacklist.clean;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martina Ciefova
 */
@Component
@EnableScheduling
@Profile("jwt")
public class BlacklistCleanScheduler {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(BlacklistCleanScheduler.class);

    private final BlacklistCleaner cleaner;

    @Autowired
    public BlacklistCleanScheduler(BlacklistCleaner cleaner) {
        this.cleaner = cleaner;
    }

    @Scheduled(cron = "${jwt.blacklist-cleaner.schedule}")
    public void clean(){
        LOGGER.debug("Blacklist clean start.");
        cleaner.clean();

        LOGGER.info("Blacklist cleaned.");
    }
}
