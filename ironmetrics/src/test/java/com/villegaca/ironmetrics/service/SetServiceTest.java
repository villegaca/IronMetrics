package com.villegaca.ironmetrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.villegaca.ironmetrics.dto.set.SetRequest;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.model.WorkoutExercise;
import com.villegaca.ironmetrics.model.WorkoutExerciseId;
import com.villegaca.ironmetrics.repository.SetRepository;

@ExtendWith (MockitoExtension.class)
public class SetServiceTest {
    
    @Mock 
    private SetRepository repo;

    @Mock
    private WorkoutExerciseService workoutExerciseService;

    @InjectMocks 
    private SetService service;
    
    private Set testSet;

    @BeforeEach
    void setUp() {
        testSet = new Set();
        testSet.setWeight(new BigDecimal("220"));
        testSet.setRepetition(8);
        testSet.setRestSeconds(90);
        testSet.setSetNumber(1);
        testSet.setCompleted(false);
    }

    // test getting a set by it's ID
    @Test 
    void getSetById_returnSetWhenFound() {
        Set expectedSet = new Set();

        when(repo.findById(1L)).thenReturn(Optional.of(expectedSet));

        Set acutalSet = service.getSetById(1L);

        assertEquals(expectedSet, acutalSet);
    }

    // test getting all sets
    @Test 
    void getAllSets_returnAllSets() {
        List<Set> sets = List.of(testSet);
        when(repo.findAll()).thenReturn(sets);

        List<Set> result = service.getAllSets();

        assertSame(sets, result);
    }

    // test creating a set
    @Test 
    void createSet_assignsNumberOneWhenNoPreviousSetExists() {
        Long workoutId = 1L;
        Long exerciseId = 2L;

        WorkoutExerciseId id = new WorkoutExerciseId(workoutId, exerciseId);
        WorkoutExercise workoutExercise = new WorkoutExercise();

        when(workoutExerciseService.getWorkoutExercise(id)).thenReturn(workoutExercise);

        when(repo
            .findFirstByWorkoutExerciseId_Id_WorkoutIdAndWorkoutExercise_Id_ExerciseIdOrderBySetNumberDesc(
                workoutId, exerciseId))
            .thenReturn(Optional.empty());
        
        when(repo.save(any(Set.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Set result = service.createSet(workoutId, exerciseId);

        assertEquals(1, result.getSetNumber());
        assertSame(workoutExercise, result.getWorkoutExercise());
    }

    // test updating a set
    @Test
    void updateSet_updatesFieldsAndSavesSet() {
        when(repo.findById(1L)).thenReturn(Optional.of(testSet));

        SetRequest request = new SetRequest(
            new BigDecimal("225"),
            10,
            120,
            true
        );

        when(repo.save(testSet)).thenReturn(testSet);

        Set result = service.updateSet(1L, request);

        assertSame(testSet, result);
        assertEquals(new BigDecimal("225"), result.getWeight());
        assertEquals(10, result.getRepetition());
        assertEquals(120, result.getRestSeconds());
        assertEquals(true, result.isCompleted());

        verify(repo).save(testSet);
    }

    // test deleting a set
    @Test 
    void deleteSet_deleteExistingSet() {
        when(repo.findById(1L)).thenReturn(Optional.of(testSet));

        service.deleteSet(1L);

        verify((repo)).delete(testSet);
    }
}
