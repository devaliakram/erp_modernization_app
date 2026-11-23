package com.example.erp.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. Password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. User authentication data, passing password - Externalized Configuration using Environment Variables.
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder,
                                                 @Value("${ADMIN_PASSWORD}") String adminPassword) {

        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode(adminPassword)).build();
        //storing the credential inmemory
        return new InMemoryUserDetailsManager(admin);
    }


    // 3. HTTP request security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // Local Postman testing only
                .csrf(csrf -> csrf.disable())

                // Do not create an HTTP session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // ADMIN and USER can read products
                        .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").hasAnyRole("ADMIN")

                        // Only ADMIN can modify products
                        .requestMatchers("/api/products", "/api/products/**").hasRole("ADMIN")

                        // All remaining endpoints need authentication
                        .anyRequest().authenticated())

                // Enable HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults())

                // Build the security filter chain
                .build();
    }
}
