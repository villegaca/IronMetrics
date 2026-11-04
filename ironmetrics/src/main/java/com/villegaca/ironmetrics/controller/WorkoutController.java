package com.villegaca.ironmetrics.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.dto.workout.WorkoutRequest;
import com.villegaca.ironmetrics.dto.workout.WorkoutResponse;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.service.WorkoutService;

import lombok.AllArgsConstructor;


@RestController 
@RequestMapping ("/api/workouts")
@AllArgsConstructor 
public class WorkoutController {
    private final WorkoutService service;

    // get workout by id
    @GetMapping ("/{workoutId}")
    public WorkoutResponse getWorkoutById(@PathVariable Long workoutId, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        Workout workout = service.getWorkoutById(workoutId, email);

        return new WorkoutResponse(
            workout.getId(),
            workout.getName(),
            workout.getStartedAt(),
            workout.getCompletedAt()
        );
    }

    // get all workouts
    @GetMapping ("/getAllWorkouts")
    public List<WorkoutResponse> getAllWorkouts (@AuthenticationPrincipal  Jwt jwt) {
        String email = jwt.getSubject();
        return service.getAllWorkouts(email)
            .stream()
            .map(workout -> new WorkoutResponse(           // for each workout entity, make a new workoutResponse
                workout.getId(),
                workout.getName(),
                workout.getStartedAt(),
                workout.getCompletedAt()
            ))
            .toList();
    }

    // create workout
    @PostMapping ("/createWorkout")
    public WorkoutResponse createWorkout(@RequestBody WorkoutRequest request, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        Workout workout = new Workout();
        workout.setName(request.name());
        workout.setStartedAt(request.startedAt());

        Workout savedWorkout = service.createWorkout(workout, email);

        return new WorkoutResponse(
            savedWorkout.getId(),
            savedWorkout.getName(),
            savedWorkout.getStartedAt(),
            savedWorkout.getCompletedAt()
        );
    }

    // update workout
    @PutMapping ("/{workoutId}")
    public WorkoutResponse updateWorkout(@PathVariable Long workoutId, @RequestBody WorkoutRequest request, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        Workout workout = new Workout();
        workout.setName(request.name());
        workout.setStartedAt(request.startedAt());
        workout.setCompletedAt(request.completedAt());

        Workout updatedWorkout = service.editWorkout(workoutId, email, workout);

        return new WorkoutResponse(
            updatedWorkout.getId(),
            updatedWorkout.getName(),
            updatedWorkout.getStartedAt(),
            updatedWorkout.getCompletedAt()
        );
    }

    // delete workout
    @DeleteMapping ("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout (@PathVariable Long workoutId, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        service.deleteWorkout(workoutId, email);
        return ResponseEntity.noContent().build();
    }
}
