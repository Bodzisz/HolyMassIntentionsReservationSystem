package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.Intentions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntentionsRepository extends JpaRepository<Intentions, Integer> {
}
