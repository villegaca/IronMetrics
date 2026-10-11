package com.villegaca.ironmetrics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.villegaca.ironmetrics.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long>{
    
    List<Workout> findByUserId(Long userId);
}
