package com.java.authService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@JsonSerialize(as = User.class)
public class User extends BaseModel {
    private String email;
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

}
