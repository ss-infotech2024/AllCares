package com.ssinfotech.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity  // Optional: enables method-level @PreAuthorize etc.
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Define an in-memory admin user
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))  // Use a strong password in production
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for API simplicity (enable and configure for production)
                .csrf(csrf -> csrf.disable())

                // Configure endpoint security rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (registration and login)
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()

                        // Addresses and user orders require authentication
                        .requestMatchers("/api/addresses/**", "/api/orders/user").authenticated()

                        // Admin-only endpoints: product CRUD and admin order management
                        .requestMatchers("/api/products/**", "/api/orders", "/api/orders/*/status").hasRole("ADMIN")

                        // Any other requests require authentication
                        .anyRequest().authenticated()
                )
                // Enable HTTP Basic authentication
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
