package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
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
    private final ChurchRepository churchRepository;

    public HolyMassServiceImpl(HolyMassRepository holyMassRepository, ChurchRepository churchRepository){
        this.holyMassRepository = holyMassRepository;
        this.churchRepository = churchRepository;
    }

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
        int churchId = holyMass.getChurch().getId();
        Optional<Church> church = churchRepository.findById(churchId);
        if (church.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Church of id=%d was not found", churchId));
        holyMass.setChurch(church.get());
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
    public void updateHolyMass(final int id, final HolyMass holyMass) {
        HolyMass existingHolyMass = holyMassRepository.findById(id).orElse(null);
        if (existingHolyMass != null) {
            int churchId = holyMass.getChurch().getId();
            Optional<Church> church = churchRepository.findById(churchId);
            if (church.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Church of id=%d was not found", churchId));
            existingHolyMass.setChurch(church.get());
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
