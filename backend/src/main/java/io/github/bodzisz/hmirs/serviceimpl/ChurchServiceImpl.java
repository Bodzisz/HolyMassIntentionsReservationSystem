package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.entity.Intention;
import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.repository.IntentionRepository;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import io.github.bodzisz.hmirs.service.ChurchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ChurchServiceImpl implements ChurchService {

    private final ChurchRepository churchRepository;
    private final HolyMassRepository holyMassRepository;
    private final ParishRepository parishRepository;
    private final IntentionRepository intentionRepository;

    public ChurchServiceImpl(ChurchRepository churchRepository,
                             HolyMassRepository holyMassRepository,
                             ParishRepository parishRepository, IntentionRepository intentionRepository){
        this.churchRepository = churchRepository;
        this.holyMassRepository = holyMassRepository;
        this.parishRepository = parishRepository;
        this.intentionRepository = intentionRepository;
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
        int parishId = church.getParish().getId();
        Optional<Parish> parish = parishRepository.findById(parishId);
        if (parish.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", parishId));
        church.setParish(parish.get());
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
            int parishId = church.getParish().getId();
            Optional<Parish> parish = parishRepository.findById(parishId);
            if (parish.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Parish of id=%d was not found", parishId));
            existingChurch.setParish(parish.get());
            existingChurch.setName(church.getName());
            existingChurch.setCity(church.getCity());
            existingChurch.setMinimalOffering(church.getMinimalOffering());
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

    @Override
    public List<Intention> getChurchIntentions(int id) {
        return intentionRepository.getIntentionByHolyMass_Church(getChurch(id));
    }

    @Override
    public List<String> getCities(){
        Set<String> uniqueCities = new HashSet<>();
        for(Church church : getChurches())
            uniqueCities.add(church.getCity());
        List<String> result = uniqueCities.stream().toList();
        Collections.sort(result);
        return result;
    }
}
