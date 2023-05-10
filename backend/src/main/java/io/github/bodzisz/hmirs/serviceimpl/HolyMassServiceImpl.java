package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.service.HolyMassService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class HolyMassServiceImpl implements HolyMassService {

    private final HolyMassRepository holyMassRepository;

    public HolyMassServiceImpl(HolyMassRepository holyMassRepository){this.holyMassRepository = holyMassRepository;}

    @Override
    public List<HolyMass> getHolyMasses() {
        return holyMassRepository.findAll();
    }

    @Override
    public HolyMass getHolyMass(int id) {
        Optional<HolyMass> search = holyMassRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("HolyMass of id=%d was not found", id));
        return search.get();
    }

    @Override
    public HolyMass addHolyMass(HolyMass holyMass) {
        return holyMassRepository.save(holyMass);
    }

    @Override
    public HolyMass deleteHolyMass(int id) {
        Optional<HolyMass> toDelete = holyMassRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("HolyMass of id=%d was not found", id));
        holyMassRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateHolyMass(final int id, HolyMass holyMass) {
        HolyMass existingHolyMass = holyMassRepository.findById(id).orElse(null);
        if (existingHolyMass != null) {
            existingHolyMass.setDate(holyMass.getDate());
            existingHolyMass.setStartTime(holyMass.getStartTime());
            existingHolyMass.setChurch(holyMass.getChurch());
            existingHolyMass.setAvailableIntentions(holyMass.getAvailableIntentions());
            holyMassRepository.save(existingHolyMass);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("HolyMass of id=%d was not found", id));
        }
    }
}
