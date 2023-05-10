package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.service.ChurchService;
import io.github.bodzisz.hmirs.serviceimpl.ChurchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/churches")
public class ChurchController {

    private final ChurchService churchService;

    public ChurchController(ChurchRepository churchRepository, HolyMassRepository holyMassRepository) {
        churchService = new ChurchServiceImpl(churchRepository, holyMassRepository);
    }

    @GetMapping
    public ResponseEntity<List<Church>> getChurches() {
        return ResponseEntity.ok(churchService.getChurches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Church> getChurch(@PathVariable final int id) {
        return ResponseEntity.ok(churchService.getChurch(id));
    }

    @GetMapping("/{id}/holymasses")
    public ResponseEntity<List<HolyMass>> getHolyMassByChurch(@PathVariable final int id) {
        return ResponseEntity.ok(churchService.getHolyMasses(id));
    }

    @PostMapping
    public ResponseEntity<Church> postChurch(@RequestBody final Church church) {
        return ResponseEntity.status(201).body(churchService.addChurch(church));
    }

    @DeleteMapping("/{id}")
    public void deleteChurch(@PathVariable int id) {
        churchService.deleteChurch(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateChurch(@PathVariable int id, @RequestBody Church updatedChurch) {
        churchService.updateChurch(id, updatedChurch);
        return ResponseEntity.status(204).build();
    }
}
