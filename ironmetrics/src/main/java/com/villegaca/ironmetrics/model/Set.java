package com.villegaca.ironmetrics.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@NoArgsConstructor 
public class Set {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal weight;

    private Integer repetition;

    private Integer setNumber;

    private Integer restSeconds;

    private boolean isCompleted;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;
}
