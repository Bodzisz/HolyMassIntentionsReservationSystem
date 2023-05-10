package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.Donation;

import java.util.List;

public interface DonationsService {
    List<Donation> getDonations();
    
    Donation getDonation(final int id);
    
    Donation addDonation(final Donation donation);
    
    Donation deleteDonation(final int id);
    
    void updateDonation(final Donation donation);
}