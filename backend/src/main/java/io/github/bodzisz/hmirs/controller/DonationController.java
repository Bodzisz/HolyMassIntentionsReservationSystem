package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Donation;
import io.github.bodzisz.hmirs.service.DonationsService;
import io.github.bodzisz.hmirs.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationsService donationsService;
    private final GoalService goalService;

    public DonationController(DonationsService donationsService, GoalService goalService) {
        this.goalService = goalService;
        this.donationsService = donationsService;
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getDonations() {
        return ResponseEntity.ok(donationsService.getDonations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonation(@PathVariable final int id) {
        return ResponseEntity.ok(donationsService.getDonation(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Donation> postDonation(@RequestBody final Donation donation) {
        donationCheck(donation);
        goalService.addProgress(goalService.getGoalByParish(donation.getParish()),donation.getAmount());
        return ResponseEntity.ok(donationsService.addDonation(donation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Donation> deleteDonation(@PathVariable int id) {
        return ResponseEntity.ok(donationsService.deleteDonation(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDonation(@PathVariable int id, @RequestBody Donation updatedDonation) {
        donationCheck(updatedDonation);
        donationsService.updateDonation(id, updatedDonation);
        return ResponseEntity.status(204).build();
    }

    private void donationCheck(Donation donation){
        if (donation.getAmount() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid donation size");
        if (donation.getUser() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        if (donation.getParish() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parish");
    }
}
