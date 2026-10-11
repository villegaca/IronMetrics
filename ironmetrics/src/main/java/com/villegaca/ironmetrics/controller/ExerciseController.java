package com.villegaca.ironmetrics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.villegaca.ironmetrics.model.Exercise;
import com.villegaca.ironmetrics.service.ExerciseService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/exercises")
@AllArgsConstructor 
public class ExerciseController {

    private final ExerciseService service;

    // get all exercises
    @GetMapping 
    public List<Exercise> getAllExercises() {
        return service.getAllExercises();
    }

    // get an exercise by id
    @GetMapping ("/{id}")
    public Exercise getExerciseById(@PathVariable Long id) {
        return service.getExerciseById(id);
    }

    // search exercises
    @GetMapping ("/search")
    public List<Exercise> searchExercise(@RequestParam String name) {
        return service.searchExercises(name);
    }
}
