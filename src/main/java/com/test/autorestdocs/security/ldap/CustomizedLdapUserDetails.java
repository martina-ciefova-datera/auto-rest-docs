package com.test.autorestdocs.security.ldap;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

/**
 *
 * @author Martina Ciefova
 */
public class CustomizedLdapUserDetails extends LdapUserDetailsImpl {

    /**
     * Original user details.
     *
     */
    private final LdapUserDetailsImpl userDetails;

    /**
     * User email address.
     *
     */
    private String email;

    /**
     * Constructor
     *
     * @param userDetails user details
     */
    public CustomizedLdapUserDetails(LdapUserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDn() {
        return userDetails.getDn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return userDetails.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return userDetails.isAccountNonExpired();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return userDetails.isAccountNonLocked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return userDetails.isCredentialsNonExpired();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return userDetails.isEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeBeforeExpiration() {
        return userDetails.getTimeBeforeExpiration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGraceLoginsRemaining() {
        return userDetails.getGraceLoginsRemaining();
    }

    /**
     * User email address.
     *
     * @return user email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * User email address.
     *
     * @param email user email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomizedLdapUserDetails other = (CustomizedLdapUserDetails) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.userDetails, other.userDetails)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.userDetails);
        hash = 71 * hash + Objects.hashCode(this.email);
        return hash;
    }
}
