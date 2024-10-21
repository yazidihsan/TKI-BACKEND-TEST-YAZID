package com.crud.demo.config;
 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 
@Configuration

// @EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())  
            .headers(headers -> headers
                .cacheControl(cache -> cache.disable())      // Disable cache-control headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames from the same origin
                .xssProtection(xss -> xss.disable())        // Disable XSS protection (careful with this!)
            )
          .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/api/users/**").authenticated()  
                // .requestMatchers("/api/users").hasRole("ROLE_ADMIN")  // Require authentication for user API endpoints
                .anyRequest().permitAll() // Allow all other requests
            );

        return http.build();
    }

    
}
