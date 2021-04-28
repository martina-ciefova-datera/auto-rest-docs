package com.test.autorestdocs.security.ldap;

import com.test.autorestdocs.security.UserDetailsImpl;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Martina Ciefova
 */
public class LdapUserMapper implements AttributesMapper<UserDetails> {

    private final LdapProperties properties;

    public LdapUserMapper(LdapProperties properties) {
        this.properties = properties;
    }

    @Override
    public UserDetails mapFromAttributes(Attributes atrbts) throws NamingException {
        String name = (String) atrbts.get("cn").get();
        List<GrantedAuthority> authorities = loadAuthorities(atrbts);

        return new UserDetailsImpl(name, null, authorities);
    }

    private List<GrantedAuthority>
            loadAuthorities(Attributes atrbts) throws NamingException {
        NamingEnumeration memberOf
                = atrbts.get(properties.getUserGroupsAttribute()).getAll();
        String stringPattern
                = MessageFormat.format(properties.getUserDnPattern(), "(.*)");

        Pattern pattern = Pattern.compile(stringPattern);

        List<GrantedAuthority> authorities = new ArrayList<>();
        while (memberOf.hasMore()) {
            Matcher matcher = pattern.matcher((String) memberOf.next());
            matcher.matches();
            authorities
                    .add(new SimpleGrantedAuthority(matcher.group(1)));
        }

        return authorities;
    }

}
