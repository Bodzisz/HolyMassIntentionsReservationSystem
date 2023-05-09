package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Donation;
import io.github.bodzisz.hmirs.repository.DonationsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationsRepository donationRepository;

    public DonationController(DonationsRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getDonations() {
        return ResponseEntity.ok(donationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Donation>> getDonation(@PathVariable final int id) {
        return ResponseEntity.ok(donationRepository.findAllById(Collections.singleton(id)));
    }

    @PostMapping
    public ResponseEntity<Donation> postDonation(@RequestBody final Donation donation) {
        return ResponseEntity.ok(donationRepository.save(donation));
    }

    @DeleteMapping("/{id}")
    public void deleteDonation(@PathVariable int id) {
        donationRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Donation updateDonation(@PathVariable int id, @RequestBody Donation updatedDonation) {
        Donation existingDonation = donationRepository.findById(id).orElse(null);
        if (existingDonation != null) {
            existingDonation.setAmount(updatedDonation.getAmount());
            existingDonation.setUser(updatedDonation.getUser());
            existingDonation.setParish(updatedDonation.getParish());
            donationRepository.save(existingDonation);
        }
        return existingDonation;
    }
}
