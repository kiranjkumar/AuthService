package com.java.authService.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private String firstName;
    private String lastName;
    private String email;
    private ResponseStatus status;

}
