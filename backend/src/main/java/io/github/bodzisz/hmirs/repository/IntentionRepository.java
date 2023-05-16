package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.Intention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntentionRepository extends JpaRepository<Intention, Integer> {
}
