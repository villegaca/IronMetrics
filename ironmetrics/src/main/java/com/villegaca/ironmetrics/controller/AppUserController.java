package com.villegaca.ironmetrics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.villegaca.ironmetrics.dto.AppUser.AppUserRequest;
import com.villegaca.ironmetrics.dto.AppUser.AppUserResponse;
import com.villegaca.ironmetrics.mapper.AppUserMapper;
import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.service.AppUserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController 
@RequestMapping ("/api/users/{userId}")
@AllArgsConstructor 
public class AppUserController {
    private final AppUserService service;

    // get user
    @GetMapping
    public ResponseEntity<AppUserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserResponseById(userId));
    }

    // edit user
    @PutMapping
    public ResponseEntity<AppUserResponse> editUser(@PathVariable Long userId, @Valid @RequestBody AppUserRequest request) {
        return ResponseEntity.ok(service.editUser(userId, request));
    }

    // delete user
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
