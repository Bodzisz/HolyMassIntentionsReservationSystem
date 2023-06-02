package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.dto.NewIntentionDTO;
import io.github.bodzisz.hmirs.entity.Intention;
import io.github.bodzisz.hmirs.service.IntentionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    private final IntentionService intentionService;

    public IntentionController(IntentionService intentionService) {
        this.intentionService = intentionService;
    }

    @GetMapping
    public ResponseEntity<List<Intention>> getIntentions() {
        return ResponseEntity.ok(intentionService.getIntentions());
    }

    @GetMapping("/church/{id}/{date}")
    public ResponseEntity<List<Intention>> getIntentionsByChurchAndDay
            (@PathVariable final int id, @PathVariable final String date) {
        String[] elems = date.split("-");
        if (elems.length != 3) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date");
        try{
            LocalDate localDate = LocalDate.of(
                    Integer.parseInt(elems[0]),
                    Integer.parseInt(elems[1]),
                    Integer.parseInt(elems[2])
            );
            return ResponseEntity.ok(intentionService.getIntentionsByChurchByDay(id, localDate));
        } catch(NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intention> getIntention(@PathVariable final int id) {
        return ResponseEntity.ok(intentionService.getIntention(id));
    }

    @PreAuthorize("hasRole('ROLE_PRIEST')")
    @PostMapping
    public ResponseEntity<Intention> postIntention(@RequestBody final NewIntentionDTO intention) {
        return ResponseEntity.ok(intentionService.addIntention(intention));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Intention> deleteIntention(@PathVariable int id) {
        return ResponseEntity.ok(intentionService.deleteIntention(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIntention(@PathVariable int id, @RequestBody Intention updatedIntention) {
        intentionCheck(updatedIntention);
        intentionService.updateIntention(id, updatedIntention);
        return ResponseEntity.status(204).build();
    }

    private void intentionCheck(Intention intention){
        if (intention.getContent() == null || intention.getContent().length() < 5)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid content");
        if (intention.getUser() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        if (intention.getHolyMass() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid holy mass");
    }
}
