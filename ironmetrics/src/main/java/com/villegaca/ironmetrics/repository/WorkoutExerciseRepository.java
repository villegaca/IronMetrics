package com.villegaca.ironmetrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId>{
    
}
