
package com.villegaca.ironmetrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import com.villegaca.ironmetrics.dto.muscle.MuscleResponse;
import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.model.ExerciseMuscle;
import com.villegaca.ironmetrics.model.Muscle;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.model.Workout;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.repository.ExerciseMuscleRepository;
import com.villegaca.ironmetrics.repository.SetRepository;

@ExtendWith(MockitoExtension.class)
class MuscleStatisticsServiceTest {

    @Mock
    private WorkoutService workoutService;

    @Mock
    private ExerciseMuscleRepository exerciseMuscleRepo;

    @Mock
    private SetRepository setRepo;

    @InjectMocks
    private MuscleStatisticsService muscleStatisticsService;

    private Workout workout;
    private WorkoutExercise workoutExercise;
    private Exercise exercise;
    private Muscle muscle;
    private ExerciseMuscle exerciseMuscle;

    @BeforeEach
    void setUp() {
        workout = mock(Workout.class);
        workoutExercise = mock(WorkoutExercise.class);
        exercise = mock(Exercise.class);
        muscle = mock(Muscle.class);
        exerciseMuscle = mock(ExerciseMuscle.class);
    }

    @Test
    void getMusclesWorked_returnsMusclesForCompletedExercises() {
        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("jane@example.com");
        when(workoutService.getAllWorkouts(jwt.getSubject())).thenReturn(List.of(workout));

        when(workout.getId()).thenReturn(1L);
        when(workout.getWorkoutExercises()).thenReturn(List.of(workoutExercise));

        when(workoutExercise.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(100L);

        Set completedSet = mock(Set.class);
        when(completedSet.isCompleted()).thenReturn(true);

        when(setRepo
                .findByWorkoutExercise_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseId(1L, 100L))
                .thenReturn(List.of(completedSet));

        when(muscle.getId()).thenReturn(10L);
        when(muscle.getName()).thenReturn("Chest");
        when(exerciseMuscle.getMuscle()).thenReturn(muscle);

        when(exerciseMuscleRepo.findByExercise_Id(100L)).thenReturn(List.of(exerciseMuscle));

        List<MuscleResponse> result = muscleStatisticsService.getMusclesWorked(jwt);

        assertEquals(1, result.size());
        verify(exerciseMuscleRepo).findByExercise_Id(100L);
    }

    @Test
    void getMusclesWorked_ignoresExercisesWithoutCompletedSets() {
        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("jane@example.com");
        when(workoutService.getAllWorkouts(jwt.getSubject())).thenReturn(List.of(workout));

        when(workout.getId()).thenReturn(1L);
        when(workout.getWorkoutExercises()).thenReturn(List.of(workoutExercise));

        when(workoutExercise.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(100L);

        Set incompleteSet = mock(Set.class);
        when(incompleteSet.isCompleted()).thenReturn(false);

        when(setRepo
                .findByWorkoutExercise_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseId(1L, 100L))
                .thenReturn(List.of(incompleteSet));

        List<MuscleResponse> result = muscleStatisticsService.getMusclesWorked(jwt);

        assertEquals(0, result.size());

        verify(exerciseMuscleRepo, never()).findByExercise_Id(anyLong());
    }

    @Test
    void getMusclesWorked_doesNotIncludeDuplicateMuscles() {
        WorkoutExercise secondWorkoutExercise = mock(WorkoutExercise.class);
        Exercise secondExercise = mock(Exercise.class);
        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("jane@example.com");

        when(workoutService.getAllWorkouts(jwt.getSubject())).thenReturn(List.of(workout));

        when(workout.getId()).thenReturn(1L);
        when(workout.getWorkoutExercises()).thenReturn(List.of(workoutExercise, secondWorkoutExercise));

        when(workoutExercise.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(100L);

        when(secondWorkoutExercise.getExercise()).thenReturn(secondExercise);
        when(secondExercise.getId()).thenReturn(200L);

        Set completedSet1 = mock(Set.class);
        Set completedSet2 = mock(Set.class);

        when(completedSet1.isCompleted()).thenReturn(true);
        when(completedSet2.isCompleted()).thenReturn(true);

        when(setRepo
                .findByWorkoutExercise_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseId(1L, 100L))
                .thenReturn(List.of(completedSet1));

        when(setRepo
                .findByWorkoutExercise_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseId(1L, 200L))
                .thenReturn(List.of(completedSet2));

        when(muscle.getId()).thenReturn(10L);
        when(muscle.getName()).thenReturn("Chest");
        when(exerciseMuscle.getMuscle()).thenReturn(muscle);

        when(exerciseMuscleRepo.findByExercise_Id(100L)).thenReturn(List.of(exerciseMuscle));

        when(exerciseMuscleRepo.findByExercise_Id(200L)).thenReturn(List.of(exerciseMuscle));

        List<MuscleResponse> result = muscleStatisticsService.getMusclesWorked(jwt);

        assertEquals(1, result.size());
        verify(exerciseMuscleRepo).findByExercise_Id(100L);
        verify(exerciseMuscleRepo).findByExercise_Id(200L);
    }

    @Test
    void getMusclesWorked_returnsEmptyListWhenUserHasNoWorkouts() {
        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("jane@example.com");
        
        when(workoutService.getAllWorkouts("jane@example.com")).thenReturn(List.of());

        List<MuscleResponse> result = muscleStatisticsService.getMusclesWorked(jwt);

        assertEquals(0, result.size());
        verifyNoInteractions(setRepo, exerciseMuscleRepo);
    }
}
