package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.Intention;

import java.util.List;

public interface IntentionService {
    List<Intention> getIntentions();
    
    Intention getIntention(final int id);
    
    Intention addIntention(final Intention intention);
    
    Intention deleteIntention(final int id);
    
    void updateIntention(final Intention intention);
}
