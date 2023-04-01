package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.payload.AuthenticationRequest;
import com.yildirim.springbootrestapi.payload.AuthenticationResponse;
import com.yildirim.springbootrestapi.payload.RegisterRequest;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
