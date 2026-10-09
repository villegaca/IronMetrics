package com.villegaca.ironmetrics.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ExerciseMuscle {
    
    @EmbeddedId 
    private ExerciseMuscleId id;

    @MapsId ("exerciseId")
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "exercise_id", nullable = false)
    private Exercise exercise;

    @MapsId ("muscleId")
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "muscle_id", nullable = false)
    private Muscle muscle;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private MuscleRole role;
}