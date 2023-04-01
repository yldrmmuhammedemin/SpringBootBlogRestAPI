package com.yildirim.springbootrestapi.controller;

import com.yildirim.springbootrestapi.payload.AuthenticationRequest;
import com.yildirim.springbootrestapi.payload.AuthenticationResponse;
import com.yildirim.springbootrestapi.payload.RegisterRequest;
import com.yildirim.springbootrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    public final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);

    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(userService.authenticate(authenticationRequest), HttpStatus.OK);

    }

}
