package com.test.autorestdocs.security.ldap;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martina Ciefova
 */
@Component
@Profile("ldap")
public class CustomizedLdapUserDetailsMapper extends LdapUserDetailsMapper {

    private final LdapProperties ldapProperties;

    /**
     * Constructor.
     *
     * @param ldapProperties ldap properties
     */
    @Autowired
    public CustomizedLdapUserDetailsMapper(LdapProperties ldapProperties) {
        this.ldapProperties = ldapProperties;
    }

    /**
     * Method mapping dir context retrieved from authentication process to user
     * details. Override method defined in {@link LdapUserDetailsMapper}.
     *
     * @param ctx dir context operation
     * @param username user name
     * @param authorities authorities list
     * @return customized user details
     */
    @Override
    public UserDetails
            mapUserFromContext(DirContextOperations ctx, String username,
                               Collection<? extends GrantedAuthority> authorities) {

        LdapUserDetailsImpl userDetails
                = (LdapUserDetailsImpl) super.mapUserFromContext(
                        ctx,
                        username,
                        authorities
                );

        CustomizedLdapUserDetails customizedLdapUserDetails
                = new CustomizedLdapUserDetails(userDetails);

        customizedLdapUserDetails.setEmail(mapEmail(
                ctx.getObjectAttribute(ldapProperties.getUserEmailAttribute())));

        return customizedLdapUserDetails;
    }

    /**
     * Cast Object.class email value to String.class
     *
     * @param emailValue email value
     * @return cast email value
     */
    private String mapEmail(Object emailValue) {

        if (emailValue != null && !(emailValue instanceof String)) {
            // Assume it's binary
            return new String((byte[]) emailValue);
        }
        return (String) emailValue;
    }
}
