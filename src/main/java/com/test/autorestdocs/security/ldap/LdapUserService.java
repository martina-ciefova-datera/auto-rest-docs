package com.test.autorestdocs.security.ldap;

import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Service
@Profile("ldap")
@EnableConfigurationProperties(LdapProperties.class)
public class LdapUserService implements ReactiveUserDetailsService {

    private final LdapTemplate template;

    private final LdapProperties properties;

    public LdapUserService(LdapTemplate template,
                           LdapProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    @Override
    public Mono<UserDetails> findByUsername(String string) {
        LdapQuery query = query()
                .base(properties.getGroupSearchBase())
                .where("objectclass").is("person").and("cn").is(string);

        List<UserDetails> users
                = template.search(query, new LdapUserMapper(properties));

        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return Mono.just(users.get(0));
    }
}
