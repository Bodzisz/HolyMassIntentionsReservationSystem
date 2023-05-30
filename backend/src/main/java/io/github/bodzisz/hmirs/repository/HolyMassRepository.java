package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolyMassRepository extends JpaRepository<HolyMass, Integer> {
    List<HolyMass> findHolyMassesByChurch(Church church);

    List<HolyMass> findHolyMassesByChurchAndDate(Church church, LocalDate date);
}
