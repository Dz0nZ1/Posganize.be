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
            "/api/v1/auth/check-auth",
            "/api/v1/stripe/webhook",
            "/generate-qr-code",
    };

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
       return http

               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       req ->
                               req.requestMatchers(whiteList)
                                       .permitAll()

                                       //Users
                                       .requestMatchers(GET, "/api/v1/users/all").hasAuthority(ADMIN_READ.name())
                                       .requestMatchers(GET, "/api/v1/users/get/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(PUT, "/api/v1/users/update/**").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/users/delete/**").hasAuthority(ADMIN_DELETE.name())

                                       //Memberships
                                       .requestMatchers(GET, "/api/v1/membership/all").hasAuthority(ADMIN_READ.name())
                                       .requestMatchers(GET, "/api/v1/membership/user/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET, "/api/v1/membership/active/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET, "/api/v1/membership/revenue-and-members").hasAuthority(ADMIN_READ.name())
                                       .requestMatchers(POST, "/api/v1/membership/create" ).hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(GET, "/api/v1/membership/get/**").hasAuthority(ADMIN_READ.name())
                                       .requestMatchers(PUT, "/api/v1/membership/update/**").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/membership/delete/**").hasAuthority(ADMIN_DELETE.name())

                                       //Schedules
                                       .requestMatchers(GET, "/api/v1/schedule/all").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET,  "/api/v1/schedule/get/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(POST, "/api/v1/schedule/create").hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(POST, "/api/v1/schedule/create/**").hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(PUT, "/api/v1/schedule/update/**").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/schedule/delete/**").hasAuthority(ADMIN_DELETE.name())

                                       //Trainings
                                       .requestMatchers(GET, "/api/v1/training/all").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET,  "/api/v1/training/get/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET,  "/api/v1/training/users-per-training").hasAuthority(ADMIN_READ.name())
                                       .requestMatchers(POST, "/api/v1/training/create").hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(PUT, "/api/v1/training/update/**").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/training/delete/**").hasAuthority(ADMIN_DELETE.name())


                                        //ClubNews
                                       .requestMatchers(GET, "/api/v1/clubnews/all").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET, "/api/v1/clubnews/pageable").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET,  "/api/v1/clubnews/get/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(POST, "/api/v1/clubnews/create").hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(PUT, "/api/v1/clubnews/update/**").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/clubnews/delete/**").hasAuthority(ADMIN_DELETE.name())


                                       .requestMatchers(GET, "/api/v1/clubrules/all").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(GET,  "/api/v1/clubrules/get/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                       .requestMatchers(POST, "/api/v1/clubrules/create").hasAuthority(ADMIN_CREATE.name())
                                       .requestMatchers(PUT, "/api/v1/clubrules/update/**").hasAuthority(ADMIN_UPDATE.name())
                                       .requestMatchers(DELETE, "/api/v1/clubrules/delete/**").hasAuthority(ADMIN_DELETE.name())


                                       .requestMatchers(POST,  "/api/v1/stripe/checkout").hasAnyAuthority(USER_CREATE.name())

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
