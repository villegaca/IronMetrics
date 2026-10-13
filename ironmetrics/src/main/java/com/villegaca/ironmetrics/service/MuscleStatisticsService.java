package com.villegaca.ironmetrics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.villegaca.ironmetrics.dto.muscle.MuscleResponse;
import com.villegaca.ironmetrics.model.ExerciseMuscle;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.repository.ExerciseMuscleRepository;
import com.villegaca.ironmetrics.repository.SetRepository;

import lombok.AllArgsConstructor;


@Service 
@AllArgsConstructor 
public class MuscleStatisticsService {
    private final WorkoutService workoutService;
    private final ExerciseMuscleRepository exerciseMuscleRepo;
    private final SetRepository setRepo;

    // get all the users workouts and their exercises
    public List<MuscleResponse> getMusclesWorked(Long userId) {
        List<Workout> workouts = workoutService.getAllWorkouts(userId);
        Map<Long, MuscleResponse> muscles = new HashMap<>();

        for (Workout workout : workouts) {
            for (WorkoutExercise workoutExercise : workout.getWorkoutExercises()) {
                Long workoutId = workout.getId();
                Long exerciseId = workoutExercise.getExercise().getId();

                List<Set> sets = setRepo.findByWorkoutExercise_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseId(workoutId, exerciseId);
            
                // only consider an exercise with at least 1 completed set
                boolean hasCompleted = sets.stream().anyMatch(Set::isCompleted);

                if(!hasCompleted) {
                    continue;
                }

                List<ExerciseMuscle> mappings = exerciseMuscleRepo.findByExercise_Id(exerciseId);

                for (ExerciseMuscle mapping : mappings) {
                    Long muscleId = mapping.getMuscle().getId();

                    muscles.putIfAbsent(
                        muscleId,
                        new MuscleResponse (
                            muscleId,
                            mapping.getMuscle().getName()
                        )
                    );
                }
            }
        }
            return new ArrayList<>(muscles.values());
    }

    // get the total volume of weights pushed
    // not sure if i want to do global statistic or for each workout
    // come back to this

}
