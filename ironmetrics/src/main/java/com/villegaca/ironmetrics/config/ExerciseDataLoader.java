package com.villegaca.ironmetrics.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;


@Component 
@RequiredArgsConstructor 
public class ExerciseDataLoader implements CommandLineRunner {
    private final ExerciseRepository exerciseRepo;

    @Override 
    public void run(String... args) {
        List<Exercise> exercises = List.of(
           creatExercise("Barbell Bench Press", "exercises/Minimalist Barbell Bench Press Demonstration.png"),
           creatExercise("Decline Barbell Bench Press", "exercises/Decline Barbell Bench Press Illustration.png"),
           creatExercise("Dumbbell Skull Crushers", "exercises/Dumbbell Skull Crushers on Bench.png"),
           creatExercise("Barbell Squat", "exercises/Featureless Athlete with Barbell Squat Setup.png"),
           creatExercise("Lat Pulldown", "exercises/Lat Pulldown Machine Exercise.png"),
           creatExercise("Incline Barbell Bench Press", "exercises/Incline Barbell Bench Press Mannequin.png"),
           creatExercise("Romanian Barbell Deadlift", "exercises/Monochrome Romanian Deadlift Illustration.png"),
           creatExercise("Seated Shoulder Press", "exercises/Seated Dumbbell Shoulder Press on Bench.png")
        ); 

        // check if exercise doesn't exist yet and save it 
        for(Exercise exercise : exercises) {
            if (!exerciseRepo.existsByNameIgnoreCase(exercise.getName())) {
                exerciseRepo.save(exercise);
            }
        }
    }

    // helper method to create exercise
    private Exercise creatExercise(String name, String imageUrl) {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setPhotoUrl(imageUrl);
        
        return exercise;
    }
}
