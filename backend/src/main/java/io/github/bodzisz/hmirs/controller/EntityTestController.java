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
    private final IntentionRepository intentionRepository;
    private final DonationsRepository donationsRepository;

    public EntityTestController(ParishRepository parishRepository, ChurchRepository churchRepository, HolyMassRepository holyMassRepository, IntentionRepository intentionRepository, DonationsRepository donationsRepository) {
        this.parishRepository = parishRepository;
        this.churchRepository = churchRepository;
        this.holyMassRepository = holyMassRepository;
        this.intentionRepository = intentionRepository;
        this.donationsRepository = donationsRepository;
    }

//    @GetMapping("/parishes")
//    public ResponseEntity<List<Parish>> getParishes() {
//        return ResponseEntity.ok(parishRepository.findAll());
//    }
//
//    @GetMapping("/churches")
//    public ResponseEntity<List<Church>> getChurches() {
//        return ResponseEntity.ok(churchRepository.findAll());
//    }
//
//    @GetMapping("/intentions")
//    public ResponseEntity<List<Intention>> getIntentions() {
//        return ResponseEntity.ok(intentionRepository.findAll());
//    }
//
//    @GetMapping("/donations")
//    public ResponseEntity<List<Donation>> getDonations() {
//        return ResponseEntity.ok(donationsRepository.findAll());
//    }
}
