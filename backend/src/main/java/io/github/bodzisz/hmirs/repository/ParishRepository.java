package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParishRepository extends JpaRepository<Parish, Integer> {
}
