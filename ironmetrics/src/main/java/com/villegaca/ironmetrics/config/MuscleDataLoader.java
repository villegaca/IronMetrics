package com.villegaca.ironmetrics.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.model.ExerciseMuscle;
import com.villegaca.ironmetrics.model.ExerciseMuscleId;
import com.villegaca.ironmetrics.model.Muscle;
import com.villegaca.ironmetrics.repository.ExerciseMuscleRepository;
import com.villegaca.ironmetrics.repository.ExerciseRepository;
import com.villegaca.ironmetrics.repository.MuscleRepository;

import lombok.RequiredArgsConstructor;


@Component 
@RequiredArgsConstructor 
public class MuscleDataLoader implements CommandLineRunner{
    private final MuscleRepository muscleRepo;
    private final ExerciseRepository exerciseRepo;
    private final ExerciseMuscleRepository exerciseMuscleRepo;

    @Override 
    public void run(String...args) {
        List<String> muscles = List.of(
            "Chest",
            "Anterior Deltoid",
            "Lateral Deltoid",
            "Posterior Deltoid",
            "Biceps Brachii",
            "Triceps Brachii",
            "Brachialis",
            "Brachioradialis",
            "Latissimus Dorsi",
            "Trapezius",
            "Rhomboids",
            "Erector Spinae",
            "Rectus Abdominis",
            "Obliques",
            "Gluteus Maximus",
            "Gluteus Medius",
            "Quadriceps",
            "Hamstrings",
            "Adductors",
            "Gastrocnemius",
            "Soleus",
            "Tibialis Anterior",
            "Forearm Flexors",
            "Forearm Extensors"
        );

        for(String name : muscles) {
            if(!muscleRepo.existsByNameIgnoreCase(name)) {
                Muscle muscle = new Muscle();
                muscle.setName(name);
                muscleRepo.save(muscle);
            }
        }

        Map<String, List<String>> assignments = Map.of(
             "Barbell Bench Press", List.of(
                "Chest",
                "Anterior Deltoid",
                "Triceps Brachii"
            ),

            "Decline Barbell Bench Press", List.of(
                "Chest",
                "Triceps Brachii",
                "Anterior Deltoid"
            ),

            "Incline Barbell Bench Press", List.of(
                "Chest",
                "Anterior Deltoid",
                "Triceps Brachii"
            ),

            "Dumbbell Skull Crushers", List.of(
                "Triceps Brachii",
                "Forearm Flexors"
            ),

            "Barbell Squat", List.of(
                "Quadriceps",
                "Gluteus Maximus",
                "Adductors",
                "Erector Spinae",
                "Hamstrings"
            ),

            "Romanian Barbell Deadlift", List.of(
                "Hamstrings",
                "Gluteus Maximus",
                "Erector Spinae",
                "Adductors",
                "Forearm Flexors"
            ),

            "Lat Pulldown", List.of(
                "Latissimus Dorsi",
                "Biceps Brachii",
                "Brachialis",
                "Brachioradialis",
                "Rhomboids",
                "Trapezius"
            ),

            "Seated Shoulder Press", List.of(
                "Anterior Deltoid",
                "Lateral Deltoid",
                "Triceps Brachii",
                "Trapezius"
            )
        );

        for(var exerciseEntry : assignments.entrySet()) {
            Exercise exercise = exerciseRepo.findByNameIgnoreCase(exerciseEntry.getKey());

            for(String muscleName : exerciseEntry.getValue()) {
                Muscle muscle = muscleRepo.findByNameIgnoreCase(muscleName);

                ExerciseMuscle relationship = new ExerciseMuscle();
                ExerciseMuscleId id = new ExerciseMuscleId(exercise.getId(), muscle.getId());

                relationship.setId(id);

                relationship.setExercise(exercise);
                relationship.setMuscle(muscle);

                if(!exerciseMuscleRepo.existsById(id)) {
                    exerciseMuscleRepo.save(relationship);
                }
            } 
        }
    }
}
