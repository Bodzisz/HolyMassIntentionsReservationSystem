package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Donation;
import io.github.bodzisz.hmirs.repository.DonationsRepository;
import io.github.bodzisz.hmirs.service.DonationsService;
import io.github.bodzisz.hmirs.serviceimpl.DonationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationsService donationsService;

    public DonationController(DonationsRepository donationRepository) {
        donationsService= new DonationServiceImpl(donationRepository);
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getDonations() {
        return ResponseEntity.ok(donationsService.getDonations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonation(@PathVariable final int id) {
        return ResponseEntity.ok(donationsService.getDonation(id));
    }

    @PostMapping
    public ResponseEntity<Donation> postDonation(@RequestBody final Donation donation) {
        return ResponseEntity.ok(donationsService.addDonation(donation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Donation> deleteDonation(@PathVariable int id) {
        return ResponseEntity.ok(donationsService.deleteDonation(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDonation(@PathVariable int id, @RequestBody Donation updatedDonation) {
        donationsService.updateDonation(id, updatedDonation);
        return ResponseEntity.status(204).build();
    }
}
