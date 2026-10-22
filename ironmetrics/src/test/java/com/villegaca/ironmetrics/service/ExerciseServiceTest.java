
package com.villegaca.ironmetrics.service;

import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.repository.ExerciseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository repo;

    @InjectMocks
    private ExerciseService exerciseService;

    // test getting all exercises
    @Test
    void getAllExercises_returnsAllExercises() {
        Exercise exercise1 = new Exercise();
        exercise1.setName("Bench Press");

        Exercise exercise2 = new Exercise();
        exercise2.setName("Squat");

        when(repo.findAll()).thenReturn(List.of(exercise1, exercise2));

        List<Exercise> result = exerciseService.getAllExercises();

        assertEquals(2, result.size());
        assertEquals("Bench Press", result.get(0).getName());
        assertEquals("Squat", result.get(1).getName());

        verify(repo).findAll();
    }

    // test getting an exercise by it's ID
    @Test
    void getExerciseById_returnsExercise_whenFound() {
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");

        when(repo.findById(1L)).thenReturn(Optional.of(exercise));

        Exercise result = exerciseService.getExerciseById(1L);

        assertEquals("Bench Press", result.getName());
        verify(repo).findById(1L);
    }

    // test searching for an exercise
    @Test
    void searchExercises_returnsMatchingExercises() {
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");

        when(repo.findByNameContainingIgnoreCase("bench")).thenReturn(List.of(exercise));

        List<Exercise> result =exerciseService.searchExercises("bench");

        assertEquals(1, result.size());
        assertEquals("Bench Press", result.get(0).getName());

        verify(repo).findByNameContainingIgnoreCase("bench");
    }

    // test creating an exercise
    @Test
    void createExercise_savesAndReturnsExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("Deadlift");
        exercise.setNotes("Keep back straight");

        when(repo.save(exercise)).thenReturn(exercise);

        Exercise result = exerciseService.createExercise(exercise);

        assertSame(exercise, result);
        verify(repo).save(exercise);
    }

    // test updating an exercise
    @Test
    void updateExercise_updatesFieldsAndSaves() {
        Exercise currentExercise = new Exercise();
        currentExercise.setName("Bench Press");
        currentExercise.setNotes("Old notes");
        currentExercise.setPhotoUrl("old-photo.jpg");

        Exercise updatedExercise = new Exercise();
        updatedExercise.setName("Incline Bench Press");
        updatedExercise.setNotes("Updated notes");
        updatedExercise.setPhotoUrl("new-photo.jpg");

        when(repo.findById(1L)).thenReturn(Optional.of(currentExercise));

        when(repo.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exercise result =exerciseService.updateExercise(1L, updatedExercise);

        assertEquals("Incline Bench Press", result.getName());
        assertEquals("Updated notes", result.getNotes());
        assertEquals("new-photo.jpg", result.getPhotoUrl());

        verify(repo).findById(1L);
        verify(repo).save(currentExercise);
    }

    // test deleting an exercise
    @Test
    void deleteExercise_deletesExercise_whenFound() {
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");

        when(repo.findById(1L)).thenReturn(Optional.of(exercise));

        exerciseService.deleteExercise(1L);

        verify(repo).findById(1L);
        verify(repo).delete(exercise);
    }
}
