package com.java.authService.dtos;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String email;
    private String password;

}
