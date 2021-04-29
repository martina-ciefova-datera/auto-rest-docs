package com.test.autorestdocs.security.jwt.blacklist;

import java.util.Date;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Martina Ciefova
 */
@Profile("jwt")
public interface BlacklistRepository extends CrudRepository<Blacklist, Long> {

    boolean existsByToken(String token);

    @Modifying
    void deleteByExpirationDateBefore(Date date);
}
