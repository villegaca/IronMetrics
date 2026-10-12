package com.villegaca.ironmetrics.dto.workoutExercise;

public record WorkoutExerciseResponse (
    Long workoutId,
    Long exerciseId,
    String exerciseName,
    String exerciseNotes
) {}
