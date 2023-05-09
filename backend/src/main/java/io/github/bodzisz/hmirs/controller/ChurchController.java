package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/churches")
public class ChurchController {

    private final ChurchRepository churchRepository;

    public ChurchController(ChurchRepository churchRepository) {
        this.churchRepository = churchRepository;
    }

    @GetMapping
    public ResponseEntity<List<Church>> getChurches() {
        return ResponseEntity.ok(churchRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Church>> getChurch(@PathVariable final int id) {
        return ResponseEntity.ok(churchRepository.findAllById(Collections.singleton(id)));
    }

    @PostMapping
    public ResponseEntity<Church> postChurch(@RequestBody final Church church) {
        return ResponseEntity.ok(churchRepository.save(church));
    }

    @DeleteMapping("/{id}")
    public void deleteChurch(@PathVariable int id) {
        churchRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Church updateChurch(@PathVariable int id, @RequestBody Church updatedChurch) {
        Church existingChurch = churchRepository.findById(id).orElse(null);
        if (existingChurch != null) {
            existingChurch.setName(updatedChurch.getName());
            existingChurch.setCity(updatedChurch.getCity());
            existingChurch.setMinimalOffering(updatedChurch.getMinimalOffering());
            existingChurch.setParish(updatedChurch.getParish());
            churchRepository.save(existingChurch);
        }
        return existingChurch;
    }
}
