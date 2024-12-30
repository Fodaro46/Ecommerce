package com.esempio.Ecommerce.config;

import com.esempio.Ecommerce.api.security.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF
                .cors(cors -> cors.disable()) // Disabilita CORS se necessario
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll() // Endpoint pubblici
                        .anyRequest().authenticated() // Tutti gli altri endpoint richiedono autenticazione
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // Sessione stateless
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Aggiungi filtro JWT

        return http.build();
    }
}
