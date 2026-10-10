package com.villegaca.ironmetrics.service;

import java.util.List;
import java.util.Optional;
import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.repository.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor 
public class ExerciseService {
    private final ExerciseRepository repo;

    // get all exercises and reutrn a list of them
    public List<Exercise> getAllExercises () {
        return repo.findAll();
    }

    // find by id and handle not being found
    public Exercise getExerciseById (Long id) {
        Optional<Exercise> exercise = repo.findById(id);
        if(exercise.isEmpty()) {
            throw new EntityNotFoundException("Exercise not found: " + id);
        }

        return exercise.get();
    }
    
    // create new exercise and save to db
    public Exercise createExercise (Exercise exercise) {
        return repo.save(exercise);
    }

    // update exercise
    public Exercise updateExercise (Long id, Exercise updatedExercise) {
        Exercise currentExercise = getExerciseById(id);

        currentExercise.setName(updatedExercise.getName());
        currentExercise.setNotes(updatedExercise.getNotes());
        currentExercise.setPhotoUrl(updatedExercise.getPhotoUrl());

        return repo.save(currentExercise);
    }

    // delete exercise
    public void deleteExercise (Long id) {
        Exercise exercise = getExerciseById(id);
        repo.delete(exercise);;
    }
}
