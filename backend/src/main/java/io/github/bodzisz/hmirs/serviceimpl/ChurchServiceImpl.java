package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.service.ChurchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ChurchServiceImpl implements ChurchService {

    private final ChurchRepository churchRepository;
    private final HolyMassRepository holyMassRepository;

    public ChurchServiceImpl(ChurchRepository churchRepository, HolyMassRepository holyMassRepository){
        this.churchRepository = churchRepository;
        this.holyMassRepository = holyMassRepository;
    }

    @Override
    public List<Church> getChurches() {
        return churchRepository.findAll();
    }

    @Override
    public Church getChurch(int id) {
        Optional<Church> search = churchRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Church of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Church addChurch(Church church) {
        return churchRepository.save(church);
    }

    @Override
    public Church deleteChurch(int id) {
        Optional<Church> toDelete = churchRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Church of id=%d was not found", id));
        churchRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateChurch(final int id, Church church) {
        Church existingChurch = churchRepository.findById(id).orElse(null);
        if (existingChurch != null) {
            existingChurch.setName(church.getName());
            existingChurch.setCity(church.getCity());
            existingChurch.setMinimalOffering(church.getMinimalOffering());
            existingChurch.setParish(church.getParish());
            churchRepository.save(existingChurch);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Church of id=%d was not found", id));
        }
    }

    @Override
    public List<HolyMass> getHolyMasses(final int id) throws ResponseStatusException{
        Church church = getChurch(id);
        return holyMassRepository.findHolyMassesByChurch(church);
    }

    @Override
    public List<Church> getChurchesByCity(final String city){
        return churchRepository.findChurchesByCity(city);
    }
}
