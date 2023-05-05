package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.*;
import io.github.bodzisz.hmirs.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
TODO Controller to remove - created only for testing entities
*/

/*
TODO For now all entities use EAGER LOADING and are not efficient, but it's just Proof Of Concept
*/
@RestController
public class EntityTestController {

    private final ParishRepository parishRepository;
    private final ChurchRepository churchRepository;
    private final HolyMassRepository holyMassRepository;
    private final IntentionsRepository intentionsRepository;
    private final DonationsRepository donationsRepository;

    public EntityTestController(ParishRepository parishRepository, ChurchRepository churchRepository, HolyMassRepository holyMassRepository, IntentionsRepository intentionsRepository, DonationsRepository donationsRepository) {
        this.parishRepository = parishRepository;
        this.churchRepository = churchRepository;
        this.holyMassRepository = holyMassRepository;
        this.intentionsRepository = intentionsRepository;
        this.donationsRepository = donationsRepository;
    }

    @GetMapping("/parishes")
    public ResponseEntity<List<Parish>> getParishes() {
        return ResponseEntity.ok(parishRepository.findAll());
    }

    @GetMapping("/churches")
    public ResponseEntity<List<Church>> getChurches() {
        return ResponseEntity.ok(churchRepository.findAll());
    }

    @GetMapping("/masses")
    public ResponseEntity<List<HolyMass>> getHolyMasses() {
        return ResponseEntity.ok(holyMassRepository.findAll());
    }

    @GetMapping("/intentions")
    public ResponseEntity<List<Intentions>> getIntentions() {
        return ResponseEntity.ok(intentionsRepository.findAll());
    }

    @GetMapping("/donations")
    public ResponseEntity<List<Donation>> getDonations() {
        return ResponseEntity.ok(donationsRepository.findAll());
    }
}
