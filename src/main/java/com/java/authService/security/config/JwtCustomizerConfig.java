package com.java.authService.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class JwtCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();

            // Add custom roles
            if (principal.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                context.getClaims().claim("roles", Arrays.asList("ADMIN"));
            } else {
                context.getClaims().claim("roles",Arrays.asList("USER"));
            }
            context.getClaims().claim("organization", "my-ecommerce");

        };
    }
}
