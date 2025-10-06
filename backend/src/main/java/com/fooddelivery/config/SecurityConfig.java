package com.fooddelivery.config;

import com.fooddelivery.security.JwtAuthFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

/**
 * Spring Security configuration: stateless JWT, BCrypt, strict CORS from env.
 * Why: Review-II security basics — hashed passwords, ORM-only queries, validated DTOs, no open CORS.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    public SecurityConfig(JwtAuthFilter f){ this.jwtAuthFilter=f; }

    @org.springframework.beans.factory.annotation.Value("${cors.allowed-origins:http://localhost:3000}")
    private String allowedOrigins;

    /**
     * Builds the security filter chain.
     * @param http http security
     * @return filter chain
     * @throws Exception on config error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(c -> c.configurationSource(corsConfig()))
            .csrf(csrf -> csrf.disable())
            .headers(h -> h.frameOptions(f -> f.disable()))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/health").permitAll()
                .requestMatchers("/api/restaurants/**","/api/menu-items/**","/api/categories/**").permitAll()
                .requestMatchers("/api/reviews/**").permitAll()
                .requestMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Strict CORS from env (comma-separated), credentials allowed, never * with credentials.
     * @return CORS source
     */
    @Bean
    public CorsConfigurationSource corsConfig(){
        CorsConfiguration c = new CorsConfiguration();
        java.util.List<String> origins = java.util.Arrays.stream(allowedOrigins.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).toList();
        c.setAllowedOrigins(origins.isEmpty() ? List.of("http://localhost:3000") : origins);
        c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
        c.setAllowedHeaders(List.of("*"));
        c.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", c);
        return src;
    }

    @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
    @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception { return cfg.getAuthenticationManager(); }
}
