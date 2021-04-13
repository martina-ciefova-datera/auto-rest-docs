package com.test.autorestdocs.security.ldap;

import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Martina Ciefova
 */
@ConstructorBinding
@ConfigurationProperties("ldap")
@Validated
public class LdapProperties {

    @NotBlank
    private final String bindUrl;

    @NotBlank
    private final String bindUsername;

    @NotBlank
    private final String bindPassword;

    @NotBlank
    private final String baseDn;

    @NotBlank
    private final String userDnPattern;

    @NotBlank
    private final String searchUserFilterPattern;

    @NotBlank
    private final String userUsernameAttribute;

    @NotBlank
    private final String userFullnameAttribute;

    @NotBlank
    private final String userEmailAttribute;

    @NotBlank
    private final String userGroupsAttribute;

    @NotBlank
    private final String userGroupNamePattern;

    @NotBlank
    private final String groupSearchBase;

    public LdapProperties(String bindUrl, String bindUsername,
                          String bindPassword, String baseDn,
                          String userDnPattern, String searchUserFilterPattern,
                          String userUsernameAttribute,
                          String userFullnameAttribute,
                          String userEmailAttribute, String userGroupsAttribute,
                          String userGroupNamePattern, String groupSearchBase) {
        this.bindUrl = bindUrl;
        this.bindUsername = bindUsername;
        this.bindPassword = bindPassword;
        this.baseDn = baseDn;
        this.userDnPattern = userDnPattern;
        this.searchUserFilterPattern = searchUserFilterPattern;
        this.userUsernameAttribute = userUsernameAttribute;
        this.userFullnameAttribute = userFullnameAttribute;
        this.userEmailAttribute = userEmailAttribute;
        this.userGroupsAttribute = userGroupsAttribute;
        this.userGroupNamePattern = userGroupNamePattern;
        this.groupSearchBase = groupSearchBase;
    }

    public String getBindUrl() {
        return bindUrl;
    }

    public String getBindUsername() {
        return bindUsername;
    }

    public String getBindPassword() {
        return bindPassword;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public String getUserDnPattern() {
        return userDnPattern;
    }

    public String getSearchUserFilterPattern() {
        return searchUserFilterPattern;
    }

    public String getUserUsernameAttribute() {
        return userUsernameAttribute;
    }

    public String getUserFullnameAttribute() {
        return userFullnameAttribute;
    }

    public String getUserEmailAttribute() {
        return userEmailAttribute;
    }

    public String getUserGroupsAttribute() {
        return userGroupsAttribute;
    }

    public String getUserGroupNamePattern() {
        return userGroupNamePattern;
    }

    public String getGroupSearchBase() {
        return groupSearchBase;
    }

}
