package com.test.autorestdocs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 *
 * @author Martina Ciefova
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
                                                AuthenticationWebFilter jwtWebFilter,
                                                AuthenticationWebFilter ldapWebFilter) {

        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(ldapWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

//    @Bean
//    public AuthenticationWebFilter userWebFilter() {
//        AuthenticationWebFilter userWebFilter
//                = new AuthenticationWebFilter(
//                        new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService()));
//        userWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/login"));
//        return userWebFilter;
//    }
}
