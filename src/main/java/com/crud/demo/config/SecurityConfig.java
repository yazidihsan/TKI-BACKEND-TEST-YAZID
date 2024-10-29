package com.crud.demo.config;
 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 
@Configuration

// @EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .cacheControl(HeadersConfigurer.CacheControlConfig::disable)      // Disable cache-control headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // Allow frames from the same origin
                .xssProtection(HeadersConfigurer.XXssConfig::disable)        // Disable XSS protection (careful with this!)
            )
          .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/api/users/**").authenticated()  
                // .requestMatchers("/api/users").hasRole("ROLE_ADMIN")  // Require authentication for user API endpoints
                .anyRequest().permitAll() // Allow all other requests
            );

        return http.build();
    }

    
}
