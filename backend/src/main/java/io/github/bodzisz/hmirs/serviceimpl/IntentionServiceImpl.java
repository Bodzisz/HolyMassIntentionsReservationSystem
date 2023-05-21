package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.dto.NewIntentionDTO;
import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.entity.Intention;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.repository.ChurchRepository;
import io.github.bodzisz.hmirs.repository.HolyMassRepository;
import io.github.bodzisz.hmirs.repository.IntentionRepository;
import io.github.bodzisz.hmirs.repository.UserRepository;
import io.github.bodzisz.hmirs.service.IntentionService;
import org.apache.tomcat.websocket.pojo.PojoMessageHandlerWholeText;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IntentionServiceImpl implements IntentionService {

    private final IntentionRepository intentionRepository;
    private final UserRepository userRepository;
    private final HolyMassRepository holyMassRepository;
    private final ChurchRepository churchRepository;

    public IntentionServiceImpl(IntentionRepository intentionRepository,
                                UserRepository userRepository,
                                HolyMassRepository holyMassRepository,
                                ChurchRepository churchRepository){
        this.intentionRepository = intentionRepository;
        this.holyMassRepository = holyMassRepository;
        this.userRepository = userRepository;
        this.churchRepository = churchRepository;
    }

    @Override
    public List<Intention> getIntentions() {
        return intentionRepository.findAll();
    }

    @Override
    public List<Intention> getIntentionsByChurchByDay(final int churchId, final LocalDate day) {
        Optional<Church> church = churchRepository.findById(churchId);
        if (church.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid church");
        List<HolyMass> holyMasses = holyMassRepository.findHolyMassesByChurchAndDate(church.get(), day);
        return intentionRepository.findIntentionsByHolyMassIn(holyMasses);
    }

    @Override
    public Intention getIntention(int id) {
        Optional<Intention> search = intentionRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Intention of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Intention addIntention(NewIntentionDTO intention) {
        int userId = intention.userId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User of id=%d was not found", userId));
        int holyMassId = intention.holyMassId();
        Optional<HolyMass> holyMassOptional = holyMassRepository.findById(holyMassId);
        if (holyMassOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Holy Mass of id=%d was not found", holyMassId));
        final HolyMass holyMass = holyMassOptional.get();
        holyMass.setAvailableIntentions(holyMass.getAvailableIntentions() - 1);

        Intention newIntention = new Intention();
        newIntention.setId(0);
        newIntention.setUser(user.get());
        newIntention.setHolyMass(holyMass);
        newIntention.setContent(intention.content());
        newIntention.setPaid(intention.isPaid());
        return intentionRepository.save(newIntention);
    }

    @Override
    public Intention deleteIntention(int id) {
        Optional<Intention> toDelete = intentionRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Intention of id=%d was not found", id));
        intentionRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateIntention(final int id, Intention intention) {
        Intention existingIntention = intentionRepository.findById(id).orElse(null);
        if (existingIntention != null) {
            int userId = intention.getUser().getId();
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User of id=%d was not found", userId));
            int holyMassId = intention.getHolyMass().getId();
            Optional<HolyMass> holyMass = holyMassRepository.findById(holyMassId);
            if (holyMass.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Holy Mass of id=%d was not found", holyMassId));
            existingIntention.setUser(user.get());
            existingIntention.setHolyMass(holyMass.get());
            existingIntention.setContent(intention.getContent());
            existingIntention.setUser(intention.getUser());
            existingIntention.setHolyMass(intention.getHolyMass());
            existingIntention.setUser(intention.getUser());
            intentionRepository.save(existingIntention);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Intention of id=%d was not found", id));
        }
    }
}
