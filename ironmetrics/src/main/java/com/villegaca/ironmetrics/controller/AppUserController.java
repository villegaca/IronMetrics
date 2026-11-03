package com.villegaca.ironmetrics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.dto.AppUser.AppUserRequest;
import com.villegaca.ironmetrics.dto.AppUser.AppUserResponse;
import com.villegaca.ironmetrics.service.AppUserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController 
@RequestMapping ("/api/user")
@AllArgsConstructor 
public class AppUserController {
    private final AppUserService service;

    // get user
    @GetMapping("/getUser")
    public ResponseEntity<AppUserResponse> getUser(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        return ResponseEntity.ok(service.getUserResponseById(email));
    }

    // edit user
    @PutMapping("/editUser")
    public ResponseEntity<AppUserResponse> editUser(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody AppUserRequest request) {
        String email = jwt.getSubject();
        return ResponseEntity.ok(service.editUser(email, request));
    }

    // delete user
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        service.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}
