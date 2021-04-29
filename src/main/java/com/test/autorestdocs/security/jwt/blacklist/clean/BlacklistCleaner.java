package com.test.autorestdocs.security.jwt.blacklist.clean;

import com.test.autorestdocs.security.jwt.blacklist.BlacklistRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martina Ciefova
 */
@Profile("jwt")
@Component
public class BlacklistCleaner {

    private final BlacklistRepository repository;

    @Autowired
    public BlacklistCleaner(BlacklistRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void clean(){
        repository.deleteByExpirationDateBefore(new Date());
    }
}
