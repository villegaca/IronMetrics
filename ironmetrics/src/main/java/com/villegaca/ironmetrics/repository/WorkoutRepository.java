package com.villegaca.ironmetrics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.villegaca.ironmetrics.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long>{

    Optional<Workout> findByIdAndUser_Email(long workoutId, String email);

    List<Workout> findByUser_Email(String email);
}
