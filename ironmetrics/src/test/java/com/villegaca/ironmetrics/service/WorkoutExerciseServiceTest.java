package com.villegaca.ironmetrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;
import com.villegaca.ironmetrics.repository.SetRepository;
import com.villegaca.ironmetrics.repository.WorkoutExerciseRepository;

@ExtendWith (MockitoExtension.class)
public class WorkoutExerciseServiceTest {

    @Mock 
    private WorkoutService workoutService;

    @Mock 
    private ExerciseService exerciseService;

    @Mock 
    WorkoutExerciseRepository repo;

    @Mock 
    SetRepository setRepo;

    @InjectMocks 
    private WorkoutExerciseService service;

    // test addExercise
    @Test 
    void addExercise_savesWorkoutExerciseAndInitialSet() {
        Long workoutId = 1L;
        Long exerciseId = 2L;

        Workout workout = new Workout();
        Exercise exercise = new Exercise();
        
        when(workoutService.getWorkoutById(1L)).thenReturn(workout);
        when(exerciseService.getExerciseById(2L)).thenReturn(exercise);

        when(repo.save(any(WorkoutExercise.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        WorkoutExercise result = service.addExercise(workoutId, exerciseId);

        assertEquals(new WorkoutExerciseId(workoutId, exerciseId), result.getId());
    }

    // test removing exercise
    @Test 
    void removeExercise_deleteExistingRelatioship() {
        WorkoutExerciseId id = new WorkoutExerciseId(1L, 2L);
        WorkoutExercise workoutExercise = new WorkoutExercise();

        workoutExercise.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(workoutExercise));

        service.removeExercise(1L, 2L);

        verify(repo).delete(workoutExercise);
    }

    // test getting all exercises
    @Test 
    void getAllExercises_returnsAllExercisesForWorkout() {
        List<WorkoutExercise> exercises = List.of(new WorkoutExercise());

        when(repo.findByWorkout_Id(1L)).thenReturn(exercises);

        List<WorkoutExercise> result = service.getAllExercises(1L);

        assertSame(exercises, result);
    }

    // test getting WorkoutExercise
    @Test 
    void getWorkoutExercise_returnsWorkoutExercise() {
        WorkoutExerciseId id = new WorkoutExerciseId(1L, 2L);
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(workoutExercise));

        WorkoutExercise result = service.getWorkoutExercise(id);

        assertSame(workoutExercise, result);
    }
}
