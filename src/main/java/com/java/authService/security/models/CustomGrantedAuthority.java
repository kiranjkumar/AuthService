package com.java.authService.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.java.authService.models.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authority;

    public CustomGrantedAuthority(Role role) {
        this.authority=role.getValue();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
