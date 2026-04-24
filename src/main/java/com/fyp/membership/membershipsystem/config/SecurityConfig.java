package com.fyp.membership.membershipsystem.config;

import com.fyp.membership.membershipsystem.service.AdminUserDetailsService;
import com.fyp.membership.membershipsystem.service.MemberUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminUserDetailsService adminUserDetailsService;
    private final MemberUserDetailsService memberUserDetailsService;

    public SecurityConfig(AdminUserDetailsService adminUserDetailsService,MemberUserDetailsService memberUserDetailsService) {
        this.adminUserDetailsService = adminUserDetailsService;
        this.memberUserDetailsService = memberUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(adminUserDetailsService)
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authManager = builder.build();

        http
                .securityMatcher("/admin/**")
                .authenticationManager(authManager)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "/admin/register").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureUrl("/admin/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );


        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain memberFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(memberUserDetailsService)
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authManager = builder.build();

        http
                .securityMatcher("/member/**")
                .authenticationManager(authManager)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/login", "/member/register").permitAll()
                        .anyRequest().hasRole("MEMBER")
                )
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login")
                        .usernameParameter("phone")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/member/dashboard", true)
                        .failureUrl("/member/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/member/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
