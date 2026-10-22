package com.villegaca.ironmetrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.repository.AppUserRepository;
import com.villegaca.ironmetrics.repository.WorkoutRepository;

@ExtendWith (MockitoExtension.class)
public class WorkoutServiceTest {
    
    @Mock 
    private WorkoutRepository repo;

    @Mock 
    private AppUserRepository userRepo;

    @InjectMocks 
    private WorkoutService service;

    // test get a workout by its ID
    @Test 
    void getWorkoutById_returnsWorkoutWhenfound() {
        Workout workout = new Workout();

        when(repo.findById(1L)).thenReturn(Optional.of(workout));

        Workout result = service.getWorkoutById(1L);

        assertSame(workout, result);
    }

    // test getting all the workouts
    @Test 
    void getAllWorkouts_returnsAllWorkouts() {
        List<Workout> workouts = List.of(new Workout(), new Workout());

        when(repo.findByUserId(1L)).thenReturn(workouts);

        List<Workout> result = service.getAllWorkouts(1L);

        assertSame(workouts, result);
    }

    // test creating a workout
    @Test 
    void createWorkout_addUserToCreatedWorkout() {
        Workout workout = new Workout();
        long userId = 1L;
        AppUser user = new AppUser();

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(repo.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Workout result = service.createWorkout(workout, userId);

        assertSame(workout, result);
        assertSame(user, result.getUser());
    }

    // test editting a workout
    @Test 
    void editWorkout_updateFieldsAndSavesWorkout() {
        Workout currentWorkout = new Workout();
        currentWorkout.setName("old");
        currentWorkout.setStartedAt(LocalDateTime.of(2026, 10, 20, 9, 0));
        currentWorkout.setCompletedAt(null);

        Workout updatedWorkout = new Workout();
        updatedWorkout.setName("Push day");
        updatedWorkout.setStartedAt(LocalDateTime.of(2026, 10, 21, 10, 0));
        updatedWorkout.setCompletedAt(LocalDateTime.of(2026, 10, 21, 11, 0));

        when(repo.findById(1L)).thenReturn(Optional.of(currentWorkout));
        when(repo.save(currentWorkout)).thenReturn(currentWorkout);

        Workout result = service.editWorkout(1L, updatedWorkout);

        assertSame(currentWorkout, result);
        assertEquals("Push day", result.getName());
    }

    // test deleting a workout
    @Test 
    void deleteWorkout() {
        Workout workout = new Workout();

        when(repo.findById(1L)).thenReturn(Optional.of(workout));

        service.deleteWorkout(1L);

        verify(repo).delete(workout);
    }
}
