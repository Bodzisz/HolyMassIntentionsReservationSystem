package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.service.HolyMassService;
import io.github.bodzisz.hmirs.serviceimpl.HolyMassServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holymasses")
public class HolyMassController {

    private final HolyMassService holyMassService;

    public HolyMassController(HolyMassRepository holyMassRepository) {
        holyMassService = new HolyMassServiceImpl(holyMassRepository);
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
        return ResponseEntity.ok(holyMassService.addHolyMass(hmass));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolyMass(@PathVariable int id) {
        holyMassService.deleteHolyMass(id);
        return ResponseEntity.status(204).build();
    }

}
