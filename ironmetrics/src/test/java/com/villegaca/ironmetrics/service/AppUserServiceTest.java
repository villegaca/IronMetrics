package com.villegaca.ironmetrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.villegaca.ironmetrics.dto.AppUser.AppUserRequest;
import com.villegaca.ironmetrics.dto.AppUser.AppUserResponse;
import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.repository.AppUserRepository;


@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserService appUserService;

    // test getting a user by it's ID
    @Test
    void getUserById_returnsUser_whenUserExists() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setFirstName("Alex");

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(user));

        AppUser result = appUserService.getUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Alex", result.getFirstName());
    }

    // test updating user info
    @Test
    void editUser_updatesUserAndReturnsResponse() {
        Long userId = 1L;

        AppUser currentUser = new AppUser();
        currentUser.setId(userId);
        currentUser.setFirstName("John");
        currentUser.setLastName("Smith");
        currentUser.setEmail("john@example.com");
        currentUser.setPassword("oldPassword");

        AppUserRequest request = new AppUserRequest(
            "Jane",
            "Doe",
            "jane@example.com",
            "newPassword"
        );

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(currentUser));

        when(appUserRepository.save(any(AppUser.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        AppUserResponse response = appUserService.editUser(userId, request);

        assertEquals("Jane", response.firstName());
        assertEquals("Doe", response.lastName());
        assertEquals("jane@example.com", response.email());

        verify(appUserRepository).findById(userId);
        verify(appUserRepository).save(currentUser);
    }

    // test deleting a user
    @Test
    void deleteUser_deletesUserById() {
        appUserService.deleteUser(1L);

        verify(appUserRepository).deleteById(1L);
    }
}
