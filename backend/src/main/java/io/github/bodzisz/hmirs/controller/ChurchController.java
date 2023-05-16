package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.service.ChurchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/churches")
public class ChurchController {

    private final ChurchService churchService;

    public ChurchController(ChurchService churchService) {
        this.churchService = churchService;
    }

    @GetMapping
    public ResponseEntity<List<Church>> getChurches() {
        return ResponseEntity.ok(churchService.getChurches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Church> getChurch(@PathVariable final int id) {
        return ResponseEntity.ok(churchService.getChurch(id));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Church>> getChurchesByCity(@PathVariable final String city) {
        return ResponseEntity.ok(churchService.getChurchesByCity(city));
    }

    @GetMapping("/{id}/holymasses")
    public ResponseEntity<List<HolyMass>> getHolyMassByChurch(@PathVariable final int id) {
        return ResponseEntity.ok(churchService.getHolyMasses(id));
    }

    @PostMapping
    public ResponseEntity<Church> postChurch(@RequestBody final Church church) {
        churchCheck(church);
        return ResponseEntity.status(201).body(churchService.addChurch(church));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Church> deleteChurch(@PathVariable int id) {
        return ResponseEntity.status(201).body(churchService.deleteChurch(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateChurch(@PathVariable int id, @RequestBody Church updatedChurch) {
        churchCheck(updatedChurch);
        churchService.updateChurch(id, updatedChurch);
        return ResponseEntity.status(204).build();
    }

    private void churchCheck(Church church){
        if (church.getCity() == null || church.getCity().length() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid city");
        if (church.getName() == null || church.getName().length() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid name");
        if (church.getParish() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parish");
    }
}
