package com.villegaca.ironmetrics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId>{
    
    List<WorkoutExercise> findByWorkout_Id(Long id);
}
