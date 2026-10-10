package com.villegaca.ironmetrics.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  
@Getter 
@Setter 
@NoArgsConstructor 
public class WorkoutExercise {
    
    @EmbeddedId 
    private WorkoutExerciseId id;

    @MapsId ("workoutId")
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "workout_id", nullable = false)
    private Workout workout;

    @MapsId ("exerciseId")
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "exercise_id", nullable = false)
    private Exercise exercise;
}
