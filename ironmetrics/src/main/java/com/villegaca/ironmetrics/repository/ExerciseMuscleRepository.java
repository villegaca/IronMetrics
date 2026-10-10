package com.villegaca.ironmetrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villegaca.ironmetrics.model.ExerciseMuscle;
import com.villegaca.ironmetrics.model.ExerciseMuscleId;

public interface ExerciseMuscleRepository extends JpaRepository<ExerciseMuscle, ExerciseMuscleId>{
    
}
