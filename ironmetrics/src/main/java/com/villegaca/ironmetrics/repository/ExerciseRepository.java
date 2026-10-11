package com.villegaca.ironmetrics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.villegaca.ironmetrics.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
    List<Exercise> findByName(String name);
}
