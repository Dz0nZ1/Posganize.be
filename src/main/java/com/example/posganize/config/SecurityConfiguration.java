package com.example.posganize.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Collections;

import static com.example.posganize.enums.RoleEnum.ADMIN;
import static com.example.posganize.enums.RoleEnum.MANAGER;
import static com.example.posganize.enums.PermissionEnum.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    private final String[] whiteList = {
            "/api/v1/auth/register",
            "/api/v1/auth/login",

            "/generate-qr-code",

            "/api/v1/users/all",
            "/api/v1/users/get/**",
            "/api/v1/users/update/**",
            "/api/v1/users/delete/**",

            "/api/v1/membership/all",
            "/api/v1/membership/create",
            "/api/v1/membership/get/**",
            "/api/v1/membership/user/**",
            "/api/v1/membership/update/**",
            "/api/v1/membership/delete/**",
            "/api/v1/membership/active/**",
            "/api/v1/membership/revenue-and-members",

            "/api/v1/schedule/all",
            "/api/v1/schedule/create",
            "/api/v1/schedule/create/**",
            "/api/v1/schedule/get/**",
            "/api/v1/schedule/update/**",
            "/api/v1/schedule/delete/**",

            "/api/v1/training/all",
            "/api/v1/training/get/**",
            "/api/v1/training/create",
            "/api/v1/training/delete/**",
            "/api/v1/training/update/**",


    };

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
       return http

               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       req ->
                               req.requestMatchers(whiteList)
                                       .permitAll()

                                       //Roles
                                       .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                       .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                                       //Manager
                                       .requestMatchers(GET, "/api/v1/management/get").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                       .requestMatchers(POST, "/api/v1/management/post").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                       .requestMatchers(PUT, "/api/v1/management/put").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/management/delete").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                                       //Admin
                                       .requestMatchers(GET, "/api/v1/admin/get").hasAuthority(ADMIN_READ.name())
                                       .requestMatchers(POST, "/api/v1/admin/post").hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(PUT, "/api/v1/admin/put").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/admin/delete").hasAuthority(ADMIN_DELETE.name())


                                       .anyRequest()
                                       .authenticated()

               ).sessionManagement(sessionAuthenticationStrategy ->
                       sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .logout(logout -> logout
                       .addLogoutHandler(logoutHandler)
                       .logoutUrl("/api/v1/auth/logout")
                       .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
               .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
               .build();
    }


    @Bean
    public CorsConfigurationSource corsConfiguration() {
        return request -> {
            org.springframework.web.cors.CorsConfiguration config =
                    new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            return config;
        };
    }
}
