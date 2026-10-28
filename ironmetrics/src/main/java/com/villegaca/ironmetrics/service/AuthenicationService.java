package com.villegaca.ironmetrics.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.villegaca.ironmetrics.dto.auth.LoginRequest;
import com.villegaca.ironmetrics.dto.auth.LoginResponse;
import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.repository.AppUserRepository;

import lombok.AllArgsConstructor;


@Service 
@AllArgsConstructor 
public class AuthenicationService {
    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // find by email
    public LoginResponse login(LoginRequest request) {
        String email = request.email();

        // find user by email
        AppUser user = userRepo.findByEmail(email)
            .orElseThrow(() ->
                new BadCredentialsException("Invalid Email or Password"));

        // check if passwords match
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid Email or Password");
        }

        // generate token
        String token = jwtService.generateToken(email);

        return new LoginResponse(token);
    }
}
