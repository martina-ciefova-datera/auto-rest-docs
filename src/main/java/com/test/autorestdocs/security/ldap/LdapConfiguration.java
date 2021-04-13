package com.test.autorestdocs.security.ldap;

import com.test.autorestdocs.security.LoginAuthenticationConverter;
import com.test.autorestdocs.security.captcha.CaptchaAuthenticationSuccessHandler;
import java.util.Arrays;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.ppolicy.PasswordPolicyAwareContextSource;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

/**
 *
 * @author Martina Ciefova
 */
@Configuration
@Profile("ldap")
@EnableConfigurationProperties(LdapProperties.class)
public class LdapConfiguration {

    /**
     * Creates password policy aware context source.
     *
     * @param ldapProperties ldap properties
     * @return password policy aware context source
     */
    @Bean
    public LdapContextSource contextSource(LdapProperties ldapProperties) {

        final PasswordPolicyAwareContextSource contextSource
                = new PasswordPolicyAwareContextSource(ldapProperties.getBindUrl());

        contextSource.setUserDn(ldapProperties.getBindUsername());
        contextSource.setPassword(ldapProperties.getBindPassword());

        contextSource.afterPropertiesSet();

        return contextSource;
    }

    /**
     * Creates bind authenticator with defined properties and filter based
     * search.
     *
     * @param contextSource context source
     * @param ldapProperties ldap properties
     * @return bind authenticator
     */
    @Bean
    public LdapAuthenticator bindAuthenticator(LdapContextSource contextSource,
                                               LdapProperties ldapProperties) {

        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
        authenticator.setUserDnPatterns(
                new String[]{ldapProperties.getUserDnPattern()}
        );
        authenticator.setUserAttributes(
                new String[]{
                    ldapProperties.getUserUsernameAttribute(),
                    ldapProperties.getUserFullnameAttribute(),
                    ldapProperties.getUserEmailAttribute()
                });

        FilterBasedLdapUserSearch filterBasedSearch
                = new FilterBasedLdapUserSearch(
                        ldapProperties.getUserDnPattern(),
                        ldapProperties.getSearchUserFilterPattern(),
                        contextSource);

        authenticator.setUserSearch(filterBasedSearch);

        return authenticator;
    }

    /**
     * Creates ldap authorities populator with defined context source.
     *
     * @param contextSource context source
     * @param ldapProperies ldap properties
     * @return ldap authorities populator
     */
    @Bean
    public LdapAuthoritiesPopulator
            ldapAuthoritiesPopulator(LdapContextSource contextSource,
                                     LdapProperties ldapProperies) {

        DefaultLdapAuthoritiesPopulator populator
                = new DefaultLdapAuthoritiesPopulator(
                        contextSource,
                        ldapProperies.getGroupSearchBase()
                );

        populator.setRolePrefix("");
        populator.setConvertToUpperCase(false);

        return populator;
    }

//    /**
//     * Creates customized ldap user details mapper.
//     *
//     * @param ldapProperies ldap properties
//     * @return customized ldap user details mapper
//     */
//    @Bean
//    public CustomizedLdapUserDetailsMapper
//            customizedLdapUserDetailsMapper(LdapProperties ldapProperies) {
//
//        return new CustomizedLdapUserDetailsMapper(ldapProperies);
//    }
    /**
     * Creates ldap authentication provider.
     *
     * @param authenticator authenticator
     * @param ldapAuthoritiesPopulator authorities populator
     * @param userDetailsMapper user details mapper
     * @return ldap authentication provider
     */
    @Bean
    public LdapAuthenticationProvider
            ldapAuthenticationProvider(LdapAuthenticator authenticator,
                                       LdapAuthoritiesPopulator ldapAuthoritiesPopulator,
                                       LdapUserDetailsMapper userDetailsMapper) {

        LdapAuthenticationProvider authenticationProvider
                = new LdapAuthenticationProvider(authenticator,
                                                 ldapAuthoritiesPopulator);

        authenticationProvider.setUserDetailsContextMapper(userDetailsMapper);

        return authenticationProvider;
    }

    @Bean
    @Primary
    public ReactiveAuthenticationManager
            ldapAuthenticationManager(LdapAuthenticationProvider ldapAuthenticationProvider) {
        return new ReactiveAuthenticationManagerAdapter(
                new ProviderManager(Arrays.asList(ldapAuthenticationProvider)));
    }

    @Bean
    public AuthenticationWebFilter
            ldapWebFilter(ReactiveAuthenticationManager ldapAuthenticationManager,
                          LoginAuthenticationConverter converter,
                          CaptchaAuthenticationSuccessHandler successHandler) {
        AuthenticationWebFilter ldapAuthFilter
                = new AuthenticationWebFilter(ldapAuthenticationManager);
        ldapAuthFilter.setServerAuthenticationConverter(converter);
        ldapAuthFilter.setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login"));
        ldapAuthFilter.setAuthenticationSuccessHandler(successHandler);
        return ldapAuthFilter;
    }

}
