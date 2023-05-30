package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import io.github.bodzisz.hmirs.entity.Intention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntentionRepository extends JpaRepository<Intention, Integer> {
    List<Intention> findIntentionsByHolyMassIn(List<HolyMass> holyMasses);

    List<Intention> getIntentionByHolyMass_Church(Church church);
}
