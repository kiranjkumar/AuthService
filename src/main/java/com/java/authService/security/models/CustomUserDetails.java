package com.java.authService.security.models;

import com.java.authService.models.Role;
import com.java.authService.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {



    private String username;
    private String password;
    private List<CustomGrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

 public CustomUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.enabled = true; // or set based on your user entity
        this.accountNonExpired = true; // or set based on your user entity
        this.accountNonLocked = true; // or set based on your user entity
        this.credentialsNonExpired = true; // or set based on your user entity
        this.authorities = new ArrayList<>();
        for(Role role : user.getRoles()){
            this.authorities.add(new CustomGrantedAuthority(role));
        }
 }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
