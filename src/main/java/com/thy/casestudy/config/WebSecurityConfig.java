package com.thy.casestudy.config;

import com.thy.casestudy.util.token.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * This class is for the security configuration of our application; allow or deny requests specifying certain rules
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //Allows API requests from the FRONT-END
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000")); // Frontend origin
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(List.of("*"));
                    return config;
                }))
                // Disable CSRF(Cross-Site Request Forgery) for API endpoints
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/authenticate", "/h2-console/**"))

                // Permit access to the specified endpoints
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/authenticate").permitAll() // Allow anyone to access /authenticate
                        .requestMatchers("/h2-console/**").permitAll() // Allow anyone to access /h2-console
                        .requestMatchers("/api/**").authenticated()   // All API requests require authentication
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // Allow frames from the same origin, This is necessary because the H2 Console is embedded in an iframe, which Spring Security blocks by default.
                )

                //Client must send all necessary information with every request.
                //For example, in the case of a stateless system using JWT (JSON Web Tokens), the client sends the JWT with each request, which contains all the information the server needs to authenticate and authorize the user.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter, meaning each request will include the token for validation and the server will not rely on session-based authentication.

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * A password encoder as a bean, so that the same encoding logic is used across the backend
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



