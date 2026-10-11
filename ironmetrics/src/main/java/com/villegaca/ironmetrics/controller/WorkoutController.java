package com.villegaca.ironmetrics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.service.WorkoutService;

import lombok.AllArgsConstructor;

@RestController 
@AllArgsConstructor 
public class WorkoutController {
    
    private final WorkoutService service;

    // get workout by id
    @GetMapping ("/{workoutId}")
    public Workout getWorkoutById(@PathVariable Long workoutId) {
        return service.getWorkoutById(workoutId);
    }

    // get all workouts
    @GetMapping  ("/api/workouts")
    public List<Workout> getAllWorkouts (@RequestParam Long userId) {
        return service.getAllWorkouts(userId);
    }

    // create workout
    @PostMapping
    public Workout createWorkout(@RequestBody Workout workout, @RequestParam Long userId) {
        return service.createWorkout(workout, userId);
    }

    // update workout
    @PostMapping ("/{workoutId}")
    public Workout updateWorkout(@PathVariable Long workoutId, @RequestBody Workout workout) {
        return service.editWorkout(workoutId, workout);
    }

    // delete workout
    @DeleteMapping ("/{workoutId}")
    public void deleteWorkout (@PathVariable Long workoutId) {
        service.deleteWorkout(workoutId);
    }
}
