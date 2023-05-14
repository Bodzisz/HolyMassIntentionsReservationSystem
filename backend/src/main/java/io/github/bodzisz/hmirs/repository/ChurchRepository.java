package io.github.bodzisz.hmirs.repository;

import io.github.bodzisz.hmirs.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChurchRepository extends JpaRepository<Church, Integer> {
    List<Church> findChurchesByCity(String city);
}
