package com.villegaca.ironmetrics.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.dto.workoutExercise.WorkoutExerciseResponse;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.service.WorkoutExerciseService;

import lombok.AllArgsConstructor;


@RestController 
@RequestMapping ("/api/workouts/{workoutId}/exercises")
@AllArgsConstructor 
public class WorkoutExerciseController {
    private final WorkoutExerciseService service;

    // get all exercises from workout
    @GetMapping 
    public List<WorkoutExerciseResponse> getAllExercisesFromWorkout(@PathVariable Long workoutId) {
        List<WorkoutExercise> workoutExercises = service.getAllExercises(workoutId);
        return workoutExercises.stream()
            .map(workoutExercise -> dtoResponse(workoutExercise))  // creates DTO for every object in workoutExercises list
            .toList();
    }

    // add exercise to workout
    @PostMapping ("/{exerciseId}")
    public WorkoutExerciseResponse addExerciseToWorkout(@PathVariable Long wokroutId, @PathVariable Long exerciseId) {
        WorkoutExercise workoutExercise = service.addExercise(wokroutId, exerciseId);

        // helper method at the bottom
        return dtoResponse(workoutExercise);
    }

    // delete exercise from workout
    @DeleteMapping ("/{exerciseId}")
    public ResponseEntity<Void> deleteExerciseFromWorkout(@PathVariable Long workoutId, @PathVariable Long exerciseId) {
        service.removeExercise(workoutId, exerciseId);
        return ResponseEntity.noContent().build();
    }

    // reorder exercises
    

    // helper method that creates and returns response DTO
    private WorkoutExerciseResponse dtoResponse (WorkoutExercise workoutExercise) {
        return new WorkoutExerciseResponse(
            workoutExercise.getWorkout().getId(),
            workoutExercise.getExercise().getId(),
            workoutExercise.getExercise().getName(),
            workoutExercise.getExercise().getNotes()
        );
    }
}
