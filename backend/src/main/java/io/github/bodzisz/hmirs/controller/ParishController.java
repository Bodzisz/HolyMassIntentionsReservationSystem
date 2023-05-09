package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/parishes")
public class ParishController {

    private final ParishRepository parishRepository;

    public ParishController(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @GetMapping
    public ResponseEntity<List<Parish>> getParishes() {
        return ResponseEntity.ok(parishRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Parish>> getParish(@PathVariable final int id) {
        return ResponseEntity.ok(parishRepository.findAllById(Collections.singleton(id)));
    }

    @PostMapping
    public ResponseEntity<Parish> postParish(@RequestBody final Parish parish) {
        return ResponseEntity.ok(parishRepository.save(parish));
    }

    @DeleteMapping("/{id}")
    public void deleteParish(@PathVariable int id) {
        parishRepository.deleteById(id);
    }
    
    @PutMapping("/{id}")
    public Parish updateParish(@PathVariable int id, @RequestBody Parish updatedParish) {
        Parish existingParish = parishRepository.findById(id).orElse(null);
        if (existingParish != null) {
            existingParish.setName(updatedParish.getName());
            existingParish.setMainPriest(updatedParish.getMainPriest());
            parishRepository.save(existingParish);
        }
        return existingParish;
    }
}
