package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.dto.NewHolyMassDTO;
import io.github.bodzisz.hmirs.dto.NewHolyMassForYearDTO;
import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.service.HolyMassService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    public HolyMass addHolyMass(final NewHolyMassDTO holyMass) {
        int churchId = holyMass.churchId();
        Church church = churchRepository.findById(churchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Church of id=%d was not found", churchId)));
        HolyMass newHolyMass = new HolyMass();
        newHolyMass.setId(0);
        newHolyMass.setChurch(church);
        newHolyMass.setDate(holyMass.date());
        newHolyMass.setStartTime(holyMass.startTime());
        newHolyMass.setAvailableIntentions(holyMass.availableIntentions());
        return holyMassRepository.save(newHolyMass);
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
    public void updateHolyMass(final int id, final NewHolyMassDTO holyMass) {
        HolyMass existingHolyMass = holyMassRepository.findById(id).orElse(null);
        if (existingHolyMass != null) {
            int churchId = holyMass.churchId();
            Church church = churchRepository.findById(churchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Church of id=%d was not found", churchId)));
            existingHolyMass.setChurch(church);
            existingHolyMass.setDate(holyMass.date());
            existingHolyMass.setStartTime(holyMass.startTime());
            existingHolyMass.setChurch(church);
            existingHolyMass.setAvailableIntentions(holyMass.availableIntentions());
            holyMassRepository.save(existingHolyMass);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("HolyMass of id=%d was not found", id));
        }
    }

    @Override
    public List<HolyMass> addHolyMassesForYear(final NewHolyMassForYearDTO holyMassDTO, int year, boolean forSundays) {
        int churchId = holyMassDTO.churchId();
        Church church = churchRepository.findById(churchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Church of id=%d was not found", churchId)));

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.getYear() == year? currentDate : LocalDate.of(year, 1, 1);

        Calendar c = Calendar.getInstance();

        List<HolyMass> toAdd = new ArrayList<>();
        Set<Integer> validDaysOfTheWeek = forSundays? new HashSet<>(List.of(1)) : new HashSet<>(Arrays.asList(2,3,4,5,6,7));

        for(LocalDate date = startDate; date.getYear() == year; date = date.plusDays(1)) {
            c.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if(validDaysOfTheWeek.contains(c.get(Calendar.DAY_OF_WEEK))) {
                toAdd.add(getHollyMassFromDto(holyMassDTO, church, date));
            }
        }

        holyMassRepository.saveAll(toAdd);

        return toAdd;
    }

    private HolyMass getHollyMassFromDto(final NewHolyMassForYearDTO holyMassDTO, final Church church, final LocalDate date) {
        final HolyMass holyMass = new HolyMass();
        holyMass.setId(0);
        holyMass.setChurch(church);
        holyMass.setAvailableIntentions(holyMassDTO.availableIntentions());
        holyMass.setDate(date);
        holyMass.setStartTime(holyMassDTO.startTime());
        return holyMass;
    }
}
