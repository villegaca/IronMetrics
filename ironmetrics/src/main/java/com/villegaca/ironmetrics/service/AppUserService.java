package com.villegaca.ironmetrics.service;

import org.springframework.stereotype.Service;

import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.repository.AppUserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class AppUserService {
    private final AppUserRepository repo;

    //find user by id and handle excpetions
    public AppUser getUserById (Long id) {
        return repo.findById(id)
            .orElseThrow(() -> 
                new EntityNotFoundException("User not found" + id));
    }

    // create new user
    public AppUser createUser (AppUser user) {
        return repo.save(user);
    }

    // update fist name
    public AppUser updateFirstName (Long id, String updatedFirstName) {
        AppUser currentUser = getUserById(id);
        currentUser.setFirstName(updatedFirstName);

        return repo.save(currentUser);
    }

    // update last name
    public AppUser updateLastName (Long id, String updatedLastName) {
        AppUser currentUser = getUserById(id);
        currentUser.setLastName(updatedLastName);

        return repo.save(currentUser);
    }

    // update email
    public AppUser updateEmail (Long id, String updatedEmail) {
        AppUser currentUser = getUserById(id);

        currentUser.setEmail(updatedEmail);

        return repo.save(currentUser);
    }

    // delete user
    public void deleteUser (Long id) {
        repo.deleteById(id);
    }
}
