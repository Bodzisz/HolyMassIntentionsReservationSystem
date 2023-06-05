package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.dto.NewDonationDTO;
import io.github.bodzisz.hmirs.entity.Donation;
import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.repository.DonationsRepository;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import io.github.bodzisz.hmirs.repository.UserRepository;
import io.github.bodzisz.hmirs.service.DonationsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationsService {

    private final DonationsRepository donationRepository;
    private final UserRepository userRepository;
    private final ParishRepository parishRepository;

    public DonationServiceImpl(DonationsRepository donationRepository,
                               UserRepository userRepository,
                               ParishRepository parishRepository){
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.parishRepository = parishRepository;
    }

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
    public Donation addDonation(NewDonationDTO donation) {
        User user = userRepository.findByLogin(donation.userLogin());
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with login %s was not found", donation.userLogin()));
        Optional<Parish> parish = parishRepository.findById(donation.parishId());
        if (parish.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", donation.parishId()));
        Donation newDonation = new Donation();
        newDonation.setAmount(donation.amount());
        newDonation.setUser(user);
        newDonation.setParish(parish.get());
        return donationRepository.save(newDonation);
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
            int userId = donation.getUser().getId();
            int parishId = donation.getParish().getId();
            Optional<User> user = userRepository.findById(userId);
            Optional<Parish> parish = parishRepository.findById(parishId);
            if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User of id=%d was not found", userId));
            else if (parish.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Parish of id=%d was not found", parishId));
            existingDonation.setUser(user.get());
            existingDonation.setParish(parish.get());
            donationRepository.save(existingDonation);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Donation of id=%d was not found", id));
        }
    }
}
