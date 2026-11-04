package com.villegaca.ironmetrics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId>{
    
    List<WorkoutExercise> findByWorkout_IdAndWorkout_User_Email(Long workoutId, String email);

    Optional<WorkoutExercise> findByIdAndWorkout_User_Email(WorkoutExerciseId id, String email);

}
