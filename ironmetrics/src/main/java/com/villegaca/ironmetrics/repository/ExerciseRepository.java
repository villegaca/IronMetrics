package com.villegaca.ironmetrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.villegaca.ironmetrics.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
}
