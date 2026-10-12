package com.villegaca.ironmetrics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;
import com.villegaca.ironmetrics.repository.SetRepository;
import com.villegaca.ironmetrics.repository.WorkoutExerciseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository repo;
    private final SetRepository setRepo;
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;

    // add exercise to workout
    public WorkoutExercise addExercise(Long workoutId, Long exerciseId) {   
        Workout workout = workoutService.getWorkoutById(workoutId);
        Exercise exercise = exerciseService.getExerciseById(exerciseId);

        WorkoutExercise workoutExercise = new WorkoutExercise();

        workoutExercise.setId(new WorkoutExerciseId(workoutId, exerciseId));
        workoutExercise.setWorkout(workout);
        workoutExercise.setExercise(exercise);
        
        Set initialSet = new Set();
        initialSet.setSetNumber(1);
        initialSet.setWorkoutExercise(workoutExercise);
        setRepo.save(initialSet);

        return repo.save(workoutExercise);
    }

    // remove an exercise from workout
    public void removeExercise(Long workoutId, Long exerciseId) {
        WorkoutExerciseId id = new WorkoutExerciseId(workoutId, exerciseId);
        WorkoutExercise workoutExercise = getWorkoutExercise(id);

        repo.delete(workoutExercise);
    }

    // retrieve all exercises from workout
    public List<WorkoutExercise> getAllExercises(Long workoutId) {
        return repo.findByWorkout_Id(workoutId);
    }

    // get workout_exercise id
    public WorkoutExercise getWorkoutExercise(WorkoutExerciseId id) {
        return repo.findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException("Exercise not found in workout: " + id));
    }
}
