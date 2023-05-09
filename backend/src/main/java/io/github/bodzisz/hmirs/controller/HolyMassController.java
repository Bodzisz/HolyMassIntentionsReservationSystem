package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/holymasses")
public class HolyMassController {

    private final HolyMassRepository holyMassRepository;

    public HolyMassController(HolyMassRepository holyMassRepository) {
        this.holyMassRepository = holyMassRepository;
    }

    @GetMapping
    public ResponseEntity<List<HolyMass>> getHolyMasses() {
        return ResponseEntity.ok(holyMassRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<HolyMass>> getHolyMass(@PathVariable final int id) {
        return ResponseEntity.ok(holyMassRepository.findAllById(Collections.singleton(id)));
    }

    @PostMapping
    public ResponseEntity<HolyMass> postHolyMass(@RequestBody final HolyMass hmass) {
        return ResponseEntity.ok(holyMassRepository.save(hmass));
    }

    @DeleteMapping("/{id}")
    public void deleteHolyMass(@PathVariable int id) {
        holyMassRepository.deleteById(id);
    }

}
