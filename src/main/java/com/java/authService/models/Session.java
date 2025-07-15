package com.java.authService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.java.authService.models.User;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel {

    private String token;

    private Date expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Enumerated(EnumType.STRING)
    private SessionStatus status;
}
