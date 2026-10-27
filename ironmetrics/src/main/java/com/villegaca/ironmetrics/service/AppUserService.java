package com.villegaca.ironmetrics.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.villegaca.ironmetrics.dto.AppUser.AppUserRequest;
import com.villegaca.ironmetrics.dto.AppUser.AppUserResponse;
import com.villegaca.ironmetrics.mapper.AppUserMapper;
import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.repository.AppUserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class AppUserService {
    private final AppUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    //find user by id and handle excpetions
    public AppUser getUserById (Long id) {
        return repo.findById(id)
            .orElseThrow(() -> 
                new EntityNotFoundException("User not found" + id));
    }

    // same as getUserById but returns a DTO
    public AppUserResponse getUserResponseById (Long id) {
        AppUser user = getUserById(id);
        return AppUserMapper.appUserResponse(user);
    }

    // create new user
    public AppUserResponse createUser (AppUserRequest request) {
        // encode password
        String encodedPassword = passwordEncoder.encode(request.password());

        AppUser user = new AppUser();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(encodedPassword);
        user.setLastName(request.lastName());

        AppUser createdUser = repo.save(user);

        // method creates DTO reponse
        return AppUserMapper.appUserResponse(createdUser);
    }

    // edit user info
    public AppUserResponse editUser(Long userId, AppUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());

        AppUser currentUser = getUserById(userId);
        currentUser.setFirstName(request.firstName());
        currentUser.setLastName(request.lastName());
        currentUser.setEmail(request.email());
        currentUser.setPassword(encodedPassword);

        AppUser updatedUser = repo.save(currentUser);

        // method creates DTO reponse
        return AppUserMapper.appUserResponse(updatedUser);
    }

    // delete user
    public void deleteUser (Long id) {
        repo.deleteById(id);
    }
}
