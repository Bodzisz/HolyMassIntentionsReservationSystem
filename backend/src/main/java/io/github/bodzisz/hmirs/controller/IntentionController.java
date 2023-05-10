package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Intention;
import io.github.bodzisz.hmirs.repository.IntentionRepository;
import io.github.bodzisz.hmirs.service.IntentionService;
import io.github.bodzisz.hmirs.serviceimpl.IntentionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    private final IntentionService intentionService;

    public IntentionController(IntentionRepository intentionRepository) {
        intentionService = new IntentionServiceImpl(intentionRepository);
    }

    @GetMapping
    public ResponseEntity<List<Intention>> getIntentions() {
        return ResponseEntity.ok(intentionService.getIntentions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intention> getIntention(@PathVariable final int id) {
        return ResponseEntity.ok(intentionService.getIntention(id));
    }

    @PostMapping
    public ResponseEntity<Intention> postIntention(@RequestBody final Intention intention) {
        return ResponseEntity.ok(intentionService.addIntention(intention));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Intention> deleteIntention(@PathVariable int id) {
        return ResponseEntity.ok(intentionService.deleteIntention(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody Intention updatedIntention) {
        intentionService.updateIntention(id, updatedIntention);
        return ResponseEntity.status(204).build();
    }

}
