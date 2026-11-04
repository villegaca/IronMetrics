package com.villegaca.ironmetrics.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.villegaca.ironmetrics.dto.set.SetRequest;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;
import com.villegaca.ironmetrics.repository.SetRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class SetService {
    private final SetRepository setRepo;
    private final WorkoutExerciseService workoutExerciseService;

    // find by id
    public Set getSetById (Long id, String email) {
        // Optional<Set> set = setRepo.findById(id);
        Optional<Set> set = setRepo.findByIdAndWorkoutExercise_Workout_User_Email(id, email);

        if(set.isEmpty()) {
            throw new EntityNotFoundException("Set not found: " + id);
        }

        return set.get();
    }

    // get all sets
    public List<Set> getAllSets(String email) {
        return setRepo.findByWorkoutExercise_Workout_User_Email(email);
    }

    // create a new set
    public Set createSet (Long workoutId, Long exerciseId, String email) {
        WorkoutExerciseId id = new WorkoutExerciseId(workoutId, exerciseId);
        WorkoutExercise workoutExercise = workoutExerciseService.getWorkoutExercise(id, email);

        // calculate set number
        int nextSetNumber = setRepo.findFirstByWorkoutExerciseId_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseIdOrderBySetNumberDesc(workoutId, exerciseId)
            .map(set -> set.getSetNumber() + 1)
            .orElse(1);

        Set set = new Set();
        set.setWorkoutExercise(workoutExercise);
        set.setSetNumber(nextSetNumber);

        return setRepo.save(set);
    }

    // update set
    public Set updateSet (Long id, String email, SetRequest request) {
        Set currentSet = getSetById(id, email);
        currentSet.setWeight(request.weight());
        currentSet.setRepetition(request.repetition());
        currentSet.setRestSeconds(request.restSeconds());
        currentSet.setCompleted(request.isCompleted());

        return setRepo.save(currentSet);
    }

    // delete a set
    public void deleteSet (Long id, String email) {
        Set set = getSetById(id, email);
        setRepo.delete(set);
    }
}
