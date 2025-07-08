package com.example.bus.repository;

import com.example.bus.model.TurnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TurnInfoRepository extends JpaRepository<TurnInfo, String> {
    List<TurnInfo> findByTurnIdStartingWith(String prefix);
}

