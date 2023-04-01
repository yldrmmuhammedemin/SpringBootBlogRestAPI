package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.User;
import com.yildirim.springbootrestapi.entity.UserRole;
import com.yildirim.springbootrestapi.payload.AuthenticationRequest;
import com.yildirim.springbootrestapi.payload.AuthenticationResponse;
import com.yildirim.springbootrestapi.payload.RegisterRequest;
import com.yildirim.springbootrestapi.repository.UserRepository;
import com.yildirim.springbootrestapi.security.config.JwtService;
import com.yildirim.springbootrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userRole(UserRole.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()

               )
       );
       var user = userRepository.findByEmail(request.getEmail())
               .orElseThrow(()->
               new UsernameNotFoundException("User not found."));
       var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
