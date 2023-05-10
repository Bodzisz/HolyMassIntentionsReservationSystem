package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Donation;
import io.github.bodzisz.hmirs.repository.DonationsRepository;
import io.github.bodzisz.hmirs.service.DonationsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationsService {

    private final DonationsRepository donationRepository;

    public DonationServiceImpl(DonationsRepository donationRepository){this.donationRepository = donationRepository;}

    @Override
    public List<Donation> getDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Donation getDonation(int id) {
        Optional<Donation> search = donationRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Donation of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Donation addDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public Donation deleteDonation(int id) {
        Optional<Donation> toDelete = donationRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Donation of id=%d was not found", id));
        donationRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateDonation(final int id, Donation donation) {
        Donation existingDonation = donationRepository.findById(id).orElse(null);
        if (existingDonation != null) {
            existingDonation.setAmount(donation.getAmount());
            existingDonation.setUser(donation.getUser());
            existingDonation.setParish(donation.getParish());
            donationRepository.save(existingDonation);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Donation of id=%d was not found", id));
        }
    }
}
