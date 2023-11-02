package com.epam.trainning.configuration;

import com.epam.trainning.model.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/about").permitAll()
                        .requestMatchers("/info").hasAuthority(Authority.VIEW_INFO.getAuthority())
                        .requestMatchers("/admin").hasAuthority(Authority.VIEW_ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .formLogin(config -> config
                        .loginPage("/login")
                        .usernameParameter("email")
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(config -> config
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .build();
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        final var authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        return authorityMapper;
    }
}