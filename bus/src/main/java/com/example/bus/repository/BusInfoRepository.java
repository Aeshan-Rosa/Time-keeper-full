package com.example.bus.repository;



import com.example.bus.model.BusInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BusInfoRepository extends JpaRepository<BusInfo, String> {
    Optional<BusInfo> findByPlateNo(String plateNo);
}


