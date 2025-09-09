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

    //if not fetched eagerly, roles will be null when serializing to JSON , causing issues
    //also when fetching user, roles should be fetched eagerly to avoid lazy loading issues
    //but this can cause performance issues if user has many roles
    //if its lazy loaded, it will be fetched only when accessed that too in one go , in the same trasaction.
    //cannot access roles outside transaction if lazy loaded.so changing to eager loading

}
