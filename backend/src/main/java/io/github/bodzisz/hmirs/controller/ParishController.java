package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.service.ParishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/parishes")
public class ParishController {

    private final ParishService parishService;

    public ParishController(ParishService parishService) {
        this.parishService = parishService;
    }

    @GetMapping
    public ResponseEntity<List<Parish>> getParishes() {
        return ResponseEntity.ok(parishService.getParishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parish> getParish(@PathVariable final int id) {
        return ResponseEntity.ok(parishService.getParish(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Parish> postParish(@RequestBody final Parish parish) {
        parishCheck(parish);
        return ResponseEntity.ok(parishService.addParish(parish));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Parish> deleteParish(@PathVariable int id) {
        return ResponseEntity.status(201).body(parishService.deleteParish(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateParish(@PathVariable int id, @RequestBody Parish updatedParish) {
        parishCheck(updatedParish);
        parishService.updateParish(id, updatedParish);
        return ResponseEntity.status(204).build();
    }

    private void parishCheck(Parish parish){
        if (parish.getName() == null || parish.getName().length() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parish name");
        if (parish.getMainPriest() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid main priest");
    }
}
