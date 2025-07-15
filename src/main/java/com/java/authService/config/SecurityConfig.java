package com.java.authService.config;

import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(auth->
                auth.requestMatchers("/auth/signup").permitAll()
                        .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for simplicity, adjust as needed
        return http.build();
        //http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).formLogin(Customizer.withDefaults());
       // http.csrf(AbstractHttpConfigurer::disable); // Disable CSRF for simplicity, adjust as needed

        //create a basic authentication filter

    }

}
