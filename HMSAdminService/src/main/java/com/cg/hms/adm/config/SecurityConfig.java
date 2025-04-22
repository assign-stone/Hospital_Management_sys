package com.cg.hms.adm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cg.hms.adm.entity.Role;
import com.cg.hms.adm.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define the Password Encoder bean for hashing the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define the DAO Authentication Provider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl); // Use our custom UserDetailsServiceImpl
        provider.setPasswordEncoder(passwordEncoder()); // Use the password encoder
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable()) // Disable CSRF (useful for stateless applications, can be enabled for stateful)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/admin/register", "/admin/login").permitAll() 
                        //.requestMatchers("/admin/email/**").hasRole(Role.ADMIN.name()) // Only users with ADMIN role can access /admin/email/** endpoints
                        //.anyRequest().authenticated() 
                        .anyRequest().permitAll() 
                )
                .formLogin(Customizer.withDefaults()) // Default form login remove comment when connecting to thymeleaf
                .httpBasic(Customizer.withDefaults()) // Basic Authentication if needed
                .authenticationProvider(daoAuthenticationProvider()) // Set the authentication provider
                .build();
    }
}
