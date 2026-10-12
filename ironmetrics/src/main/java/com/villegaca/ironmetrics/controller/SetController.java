package com.villegaca.ironmetrics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.dto.set.SetRequest;
import com.villegaca.ironmetrics.dto.set.SetResponse;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.service.SetService;

import lombok.AllArgsConstructor;


@RestController 
@AllArgsConstructor 
public class SetController {
    private final SetService service;

    // create a set
    @PostMapping("/api/workouts/{workoutId}/exercises/{exerciseId}/sets")
    public SetResponse createSet (@PathVariable Long workoutId, @PathVariable Long exerciseId) {
        Set newSet = service.createSet(workoutId, exerciseId);

        return new SetResponse(
            newSet.getId(),
            newSet.getWeight(),
            newSet.getRepetition(),
            newSet.getSetNumber(),
            newSet.getRestSeconds(),
            newSet.isCompleted()
        );
    }

    // edit a set
    @PutMapping ("/api/set/{setId}")
    public SetResponse editSet (@PathVariable Long setId, @RequestBody SetRequest request) {
        Set updatedSet = service.updateSet(setId, request);

        return new SetResponse(
            updatedSet.getId(),
            updatedSet.getWeight(),
            updatedSet.getRepetition(),
            updatedSet.getSetNumber(),
            updatedSet.getRestSeconds(),
            updatedSet.isCompleted()
        );
    }

    // delete a set
    @DeleteMapping ("/api/set/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable Long setId) {
        service.deleteSet(setId);
        return ResponseEntity.noContent().build();
    }
}
