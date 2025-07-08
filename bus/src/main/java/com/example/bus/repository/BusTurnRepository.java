package com.example.bus.repository;


import com.example.bus.model.BusTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BusTurnRepository extends JpaRepository<BusTurn, String> {
    Optional<BusTurn> findByBusId(String busId);
    List<BusTurn> findByTurnIdStartingWith(String prefix);
}

