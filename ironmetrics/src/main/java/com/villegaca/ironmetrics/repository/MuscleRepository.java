package com.villegaca.ironmetrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.villegaca.ironmetrics.model.Muscle;

public interface MuscleRepository extends JpaRepository<Muscle, Long>{
    
    boolean existsByNameIgnoreCase(String name);

    Muscle findByNameIgnoreCase(String name);
}
