package com.buildcraft.project.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers(
                            HttpMethod.GET,
                            "/api/v1/projects/**"
                    ).hasAnyRole(
                            "CLIENT",
                            "PROJECT_MANAGER",
                            "SUPER_ADMIN"
                    );

                    auth.requestMatchers(
                            HttpMethod.PUT,
                            "/api/v1/projects/**"
                    ).hasAnyRole(
                            "PROJECT_MANAGER",
                            "SUPER_ADMIN"
                    );

                    auth.requestMatchers(
                            HttpMethod.DELETE,
                            "/api/v1/projects/**"
                    ).hasRole("SUPER_ADMIN");

                    auth.anyRequest().authenticated();
                });

        return http.build();
    }
}
