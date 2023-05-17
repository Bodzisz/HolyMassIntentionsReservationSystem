package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.service.HolyMassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/holymasses")
@CrossOrigin(origins = "http://localhost:3000")
public class HolyMassController {

    private final HolyMassService holyMassService;

    public HolyMassController(HolyMassService holyMassService) {
        this.holyMassService = holyMassService;
    }

    @GetMapping
    public ResponseEntity<List<HolyMass>> getHolyMasses() {
        return ResponseEntity.ok(holyMassService.getHolyMasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolyMass> getHolyMass(@PathVariable final int id) {
        return ResponseEntity.ok(holyMassService.getHolyMass(id));
    }

    @PostMapping
    public ResponseEntity<HolyMass> postHolyMass(@RequestBody final HolyMass hmass) {
        holyMassCheck(hmass);
        return ResponseEntity.ok(holyMassService.addHolyMass(hmass));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolyMass(@PathVariable int id) {
        holyMassService.deleteHolyMass(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody HolyMass updatedHolyMass) {
        holyMassCheck(updatedHolyMass);
        holyMassService.updateHolyMass(id, updatedHolyMass);
        return ResponseEntity.status(204).build();
    }

    private void holyMassCheck(HolyMass holyMass){
        if (holyMass.getDate() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date");
        if (holyMass.getStartTime() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid start hour");
        if (holyMass.getChurch() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid church");
        if (holyMass.getAvailableIntentions() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid intention amount");
    }

}
