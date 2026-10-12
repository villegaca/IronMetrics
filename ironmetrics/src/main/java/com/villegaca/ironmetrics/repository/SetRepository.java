package com.villegaca.ironmetrics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.villegaca.ironmetrics.model.Set;

public interface SetRepository extends JpaRepository<Set, Long>{
    List<Set> findByWorkoutExerciseId(Long workoutId, Long exerciseId);

    Optional<Set> findFirstByWorkoutExerciseId_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseIdOrderBySetNumberDesc(
        Long workoutId, 
        Long exerciseId
    );
}
