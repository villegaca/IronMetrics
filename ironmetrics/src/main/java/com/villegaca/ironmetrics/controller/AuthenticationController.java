package com.villegaca.ironmetrics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.dto.auth.LoginRequest;
import com.villegaca.ironmetrics.dto.auth.LoginResponse;
import com.villegaca.ironmetrics.service.AuthenicationService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping ("/api/auth")
@AllArgsConstructor 
public class AuthenticationController {
    private final AuthenicationService authService;

    @PostMapping ("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
