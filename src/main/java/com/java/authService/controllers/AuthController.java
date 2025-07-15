package com.java.authService.controllers;

import com.java.authService.dtos.*;
import com.java.authService.excetions.UserAlreadyExistsException;
import com.java.authService.excetions.newInvalidUsernameException;
import com.java.authService.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponse signupResponse = new SignupResponse();
        try {
            if (authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword())) {
                signupResponse.setStatus(ResponseStatus.SUCCESS);
            } else {
                signupResponse.setStatus(ResponseStatus.FAILURE);
            }
        } catch (UserAlreadyExistsException e) {
            //create a response entity with CONFLICT status
            signupResponse.setStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(signupResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            //create a response entity with INTERNAL_SERVER_ERROR status
            signupResponse.setStatus(ResponseStatus.ERROR);
            return new ResponseEntity<>(signupResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(signupResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            String token=authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            MultiValueMap<String, String> map=new LinkedMultiValueMap<>();
            map.add("AUTHORIZATION", token);
            new ResponseEntity<>(loginResponse, map,HttpStatus.OK);

        } catch (newInvalidUsernameException e) {
            loginResponse.setResponseStatus(ResponseStatus.ERROR);
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            loginResponse.setResponseStatus(ResponseStatus.ERROR);
            return new ResponseEntity<>(loginResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(new LoginResponse());
    }
}
