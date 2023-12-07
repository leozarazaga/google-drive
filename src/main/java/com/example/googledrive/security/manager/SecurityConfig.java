package com.example.googledrive.security.manager;

import com.example.googledrive.repository.UserRepository;
import com.example.googledrive.security.filter.JWTAuthorizationFilter;
import com.example.googledrive.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up security-related beans and defining security policies.
 * Enables web security and provides configurations for authentication and authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain to handle HTTP security, authentication, and authorization.
     *
     * @param security           The HttpSecurity object for configuring security.
     * @param userDetailsService The UserDetailsService bean for loading user details.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, UserDetailsService userDetailsService) throws Exception {
        security
                .csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTAuthorizationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login", "/user/create").permitAll()
                        .anyRequest().authenticated());

        return security.build();
    }

    /**
     * Configures the authentication provider with the specified UserDetailsService and PasswordEncoder.
     *
     * @param userService The UserDetailsService bean for loading user details.
     * @param encoder     The PasswordEncoder bean for encoding passwords.
     * @return The configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userService, PasswordEncoder encoder) {
        var dao = new DaoAuthenticationProvider();

        dao.setUserDetailsService(userService);
        dao.setPasswordEncoder(encoder);

        return dao;
    }

    /**
     * Creates and returns the UserDetailsService bean.
     *
     * @param encoder        The PasswordEncoder bean for encoding passwords.
     * @param userRepository The UserRepository for interacting with user data.
     * @return The UserDetailsService bean.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder, UserRepository userRepository) {
        return new UserService(userRepository, encoder);
    }

    /**
     * Creates and returns the PasswordEncoder bean.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

