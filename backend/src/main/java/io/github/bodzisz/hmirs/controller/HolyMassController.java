package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.dto.NewHolyMassDTO;
import io.github.bodzisz.hmirs.dto.NewHolyMassForYearDTO;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.service.HolyMassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/holymasses")
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
    public ResponseEntity<HolyMass> postHolyMass(@RequestBody final NewHolyMassDTO hmass) {
        return ResponseEntity.ok(holyMassService.addHolyMass(hmass));
    }

    @PostMapping("/addForYear/{year}")
    public ResponseEntity<List<HolyMass>> addHolyMassesForYear(@RequestBody final NewHolyMassForYearDTO hmass, @PathVariable("year") int year,
                                                               @RequestParam(required = false, name = "forSundays") String forSundays) {
        boolean forSundaysValue = Boolean.parseBoolean(forSundays);
        return ResponseEntity.ok(holyMassService.addHolyMassesForYear(hmass, year, forSundaysValue));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolyMass(@PathVariable int id) {
        holyMassService.deleteHolyMass(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHolyMass(@PathVariable int id, @RequestBody NewHolyMassDTO updatedHolyMass) {
        holyMassService.updateHolyMass(id, updatedHolyMass);
        return ResponseEntity.status(204).build();
    }

}
