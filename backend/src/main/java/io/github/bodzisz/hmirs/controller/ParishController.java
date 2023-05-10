package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import io.github.bodzisz.hmirs.service.ParishService;
import io.github.bodzisz.hmirs.serviceimpl.ParishServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/parishes")
public class ParishController {

    private final ParishService parishService;

    public ParishController(ParishRepository parishRepository) {
        parishService = new ParishServiceImpl(parishRepository);
    }

    @GetMapping
    public ResponseEntity<List<Parish>> getParishes() {
        return ResponseEntity.ok(parishService.getParishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parish> getParish(@PathVariable final int id) {
        return ResponseEntity.ok(parishService.getParish(id));
    }

    @PostMapping
    public ResponseEntity<Parish> postParish(@RequestBody final Parish parish) {
        return ResponseEntity.ok(parishService.addParish(parish));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Parish> deleteParish(@PathVariable int id) {
        return ResponseEntity.status(201).body(parishService.deleteParish(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateParish(@PathVariable int id, @RequestBody Parish updatedParish) {
        parishService.updateParish(id, updatedParish);
        return ResponseEntity.status(204).build();
    }
}
