package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Intention;
import io.github.bodzisz.hmirs.repository.IntentionRepository;
import io.github.bodzisz.hmirs.service.IntentionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class IntentionServiceImpl implements IntentionService {

    private final IntentionRepository intentionRepository;

    public IntentionServiceImpl(IntentionRepository intentionRepository){this.intentionRepository = intentionRepository;}

    @Override
    public List<Intention> getIntentions() {
        return intentionRepository.findAll();
    }

    @Override
    public Intention getIntention(int id) {
        Optional<Intention> search = intentionRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Intention of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Intention addIntention(Intention intention) {
        return intentionRepository.save(intention);
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
