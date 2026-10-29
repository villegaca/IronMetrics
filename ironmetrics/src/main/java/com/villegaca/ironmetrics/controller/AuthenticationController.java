package com.villegaca.ironmetrics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.dto.AppUser.AppUserRequest;
import com.villegaca.ironmetrics.dto.AppUser.AppUserResponse;
import com.villegaca.ironmetrics.dto.auth.LoginRequest;
import com.villegaca.ironmetrics.dto.auth.LoginResponse;
import com.villegaca.ironmetrics.service.AppUserService;
import com.villegaca.ironmetrics.service.AuthenicationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping ("/api/auth")
@AllArgsConstructor 
public class AuthenticationController {
    private final AuthenicationService authService;
    private final AppUserService userService;

    // login
    @PostMapping ("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // register
    @PostMapping ("/register")
    public AppUserResponse register(@Valid @RequestBody AppUserRequest request) {
        return userService.createUser(request);
    }
}
