package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.HolyMass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolyMassRepository extends JpaRepository<HolyMass, Integer> {
}
