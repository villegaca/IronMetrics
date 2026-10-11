package com.villegaca.ironmetrics.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.villegaca.ironmetrics.model.Set;
import com.villegaca.ironmetrics.repository.SetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class SetService {
    private final SetRepository repo;

    // find by id
    public Set getSetById (Long id) {
        Optional<Set> set = repo.findById(id);

        if(set.isEmpty()) {
            throw new EntityNotFoundException("Set not found: " + id);
        }

        return set.get();
    }

    // get all sets
    public List<Set> getAllSets () {
        return repo.findAll();
    }

    // create a new set
    public Set createSet (Set set) {
        return repo.save(set);
    }

    // update set
    public Set updateSet (Long id, Set updatedSet) {
        Set currentSet = getSetById(id);

        currentSet.setWeight(updatedSet.getWeight());
        currentSet.setRepetition(updatedSet.getRepetition());
        currentSet.setSetNumber(updatedSet.getSetNumber());
        currentSet.setRestSeconds(updatedSet.getRestSeconds());
        currentSet.setCompleted(updatedSet.isCompleted());

        return repo.save(currentSet);
    }

    // delete a set
    public void deleteSet (Long id) {
        Set set = getSetById(id);
        repo.delete(set);
    }
}
