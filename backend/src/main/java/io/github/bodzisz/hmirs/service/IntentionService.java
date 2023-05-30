package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.dto.NewIntentionDTO;
import io.github.bodzisz.hmirs.entity.Intention;

import java.time.LocalDate;
import java.util.List;

public interface IntentionService {
    List<Intention> getIntentions();

    List<Intention> getIntentionsByChurchByDay(final int churchId, final LocalDate day);
    
    Intention getIntention(final int id);
    
    Intention addIntention(final NewIntentionDTO intention);
    
    Intention deleteIntention(final int id);
    
    void updateIntention(final int id, final Intention intention);
}
