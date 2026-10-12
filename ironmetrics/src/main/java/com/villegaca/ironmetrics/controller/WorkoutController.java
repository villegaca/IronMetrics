package com.villegaca.ironmetrics.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public WorkoutResponse getWorkoutById(@PathVariable Long workoutId) {
        Workout workout = service.getWorkoutById(workoutId);

        return new WorkoutResponse(
            workout.getId(),
            workout.getName(),
            workout.getStartedAt(),
            workout.getCompletedAt()
        );
    }

    // get all workouts
    @GetMapping
    public List<WorkoutResponse> getAllWorkouts (@RequestParam Long userId) {

        return service.getAllWorkouts(userId)
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
    @PostMapping
    public WorkoutResponse createWorkout(@RequestBody WorkoutRequest request, @RequestParam Long userId) {
        Workout workout = new Workout();
        workout.setName(request.name());
        workout.setStartedAt(request.startedAt());

        Workout savedWorkout = service.createWorkout(workout, userId);

        return new WorkoutResponse(
            savedWorkout.getId(),
            savedWorkout.getName(),
            savedWorkout.getStartedAt(),
            savedWorkout.getCompletedAt()
        );
    }

    // update workout
    @PutMapping ("/{workoutId}")
    public WorkoutResponse updateWorkout(@PathVariable Long workoutId, @RequestBody WorkoutRequest request) {

        Workout workout = new Workout();
        workout.setName(request.name());
        workout.setStartedAt(request.startedAt());
        workout.setCompletedAt(request.completedAt());

        Workout updatedWorkout = service.editWorkout(workoutId, workout);

        return new WorkoutResponse(
            updatedWorkout.getId(),
            updatedWorkout.getName(),
            updatedWorkout.getStartedAt(),
            updatedWorkout.getCompletedAt()
        );
    }

    // delete workout
    @DeleteMapping ("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout (@PathVariable Long workoutId) {
        
        service.deleteWorkout(workoutId);
        return ResponseEntity.noContent().build();
    }
}
