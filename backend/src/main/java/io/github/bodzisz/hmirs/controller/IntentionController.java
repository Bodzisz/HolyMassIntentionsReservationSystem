package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Intention;
import io.github.bodzisz.hmirs.repository.IntentionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    private final IntentionRepository intentionRepository;

    public IntentionController(IntentionRepository intentionRepository) {
        this.intentionRepository = intentionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Intention>> getIntentions() {
        return ResponseEntity.ok(intentionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Intention>> getIntention(@PathVariable final int id) {
        return ResponseEntity.ok(intentionRepository.findAllById(Collections.singleton(id)));
    }

    @PostMapping
    public ResponseEntity<Intention> postIntention(@RequestBody final Intention intention) {
        return ResponseEntity.ok(intentionRepository.save(intention));
    }

    @DeleteMapping("/{id}")
    public void deleteIntention(@PathVariable int id) {
        intentionRepository.deleteById(id);
    }

}
