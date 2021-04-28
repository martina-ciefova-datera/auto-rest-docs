package com.test.autorestdocs.security;

import com.test.autorestdocs.security.jwt.JwtAuthenticationConverter;
import com.test.autorestdocs.security.jwt.JwtAuthenticationManager;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

/**
 *
 * @author Martina Ciefova
 */
@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain
            springWebFilterChain(ServerHttpSecurity http,
                                 Optional<JwtAuthenticationConverter> jwtConverter,
                                 Optional<JwtAuthenticationManager> jwtManager,
                                 ServerLogoutSuccessHandler logoutSuccessHandler) {

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and();

        if (jwtManager.isPresent()) {
            http.addFilterBefore(
                    createJwtWebFilter(jwtManager.get(), jwtConverter.get()),
                    SecurityWebFiltersOrder.AUTHENTICATION);
        }

        return http.build();
    }

    // when registered as bean authentication is wired twice
    public AuthenticationWebFilter
            createJwtWebFilter(JwtAuthenticationManager manager,
                               JwtAuthenticationConverter converter) {
        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(manager);
        jwtAuthFilter.setServerAuthenticationConverter(converter);
        return jwtAuthFilter;
    }

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User
//                .withUsername("spravcenipez")
//                .password("{noop}701E8a6e1b")
//                .roles("spravci-nipez")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }
//
//    @Bean
//    @Primary
//    public ReactiveAuthenticationManager
//            manager(ReactiveUserDetailsService userDetailsService) {
//        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
//    }
}
