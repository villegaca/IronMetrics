package com.villegaca.ironmetrics.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@NoArgsConstructor 
@EqualsAndHashCode 
public class WorkoutExerciseId implements Serializable{
    
    private Long workoutId;

    private Long exerciseId;
}
