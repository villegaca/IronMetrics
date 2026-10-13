package com.villegaca.ironmetrics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villegaca.ironmetrics.model.ExerciseMuscle;
import com.villegaca.ironmetrics.model.ExerciseMuscleId;

public interface ExerciseMuscleRepository extends JpaRepository<ExerciseMuscle, ExerciseMuscleId>{
    List<ExerciseMuscle> findByExercise_Id(Long exerciseId);
}
