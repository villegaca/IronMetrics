package com.villegaca.ironmetrics.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.villegaca.ironmetrics.model.AppUser;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class WorkoutService {
    private final WorkoutRepository workoutRepo;
    private final AppUserService userService;

    // find by id
    public Workout getWorkoutById (Long id) {
        return workoutRepo.findById(id)
            .orElseThrow(() -> 
                new EntityNotFoundException("Workout not found: " + id)); 
    }

    // find all workouts with user email
    public List<Workout> getAllWorkouts(String email) {
        return workoutRepo.findByEmail(email);
    }

    // create and add user connection
    public Workout createWorkout (Workout workout, String email) {
        AppUser user = userService.getUserByEmail(email);
        workout.setUser(user);
        return workoutRepo.save(workout);
    }

    // edit workout
    public Workout editWorkout (Long id, Workout updatedWorkout) {
        Workout currWorkout = getWorkoutById(id);

        currWorkout.setCompletedAt(updatedWorkout.getCompletedAt());
        currWorkout.setName(updatedWorkout.getName());
        currWorkout.setStartedAt(updatedWorkout.getStartedAt());

        return workoutRepo.save(currWorkout);
    }


    // delete
    public void deleteWorkout (Long id) {
        Workout workout = getWorkoutById(id);
        workoutRepo.delete(workout);
    }
}
