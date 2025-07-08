package com.example.bus.controller;

import com.example.bus.model.BusTurn;
import com.example.bus.repository.BusTurnRepository;
import com.example.bus.repository.BusInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turns")
@CrossOrigin("*")
public class BusTurnController {

    @Autowired
    private BusTurnRepository busTurnRepo;

    @Autowired
    private BusInfoRepository busInfoRepo;

    //   Get all
    @GetMapping("/all")
    public ResponseEntity<List<BusTurn>> getAllTurns() {
        return ResponseEntity.ok(busTurnRepo.findAll());
    }

    //add a turn
    @PostMapping("/add")
    public ResponseEntity<String> addTurn(@RequestBody BusTurn newTurn) {
        String busId = newTurn.getBusId();
        String turnId = newTurn.getTurnId();

        // 1. Check if BUS_ID exists in Bus_info
        if (!busInfoRepo.existsById(busId)) {
            return ResponseEntity.badRequest().body("❌ Bus ID '" + busId + "' does not exist in Bus_info.");
        }

        // 2. Check if the BUS_ID is already assigned a TURN_ID
        boolean isBusAlreadyAssigned = busTurnRepo.findAll().stream()
                .anyMatch(bt -> bt.getBusId().equals(busId));

        if (isBusAlreadyAssigned) {
            return ResponseEntity.badRequest().body("❌ This bus is already assigned to a turn. Cannot assign again.");
        }

        // 3. Check if the TURN_ID already exists
        if (busTurnRepo.existsById(turnId)) {
            return ResponseEntity.badRequest().body("❌ Turn ID '" + turnId + "' already exists.");
        }

        // All checks passed
        busTurnRepo.save(newTurn);
        return ResponseEntity.ok(" New turn added successfully.");
    }

    // Update existing turn
    @PutMapping("/update/{turnId}")
    public ResponseEntity<String> updateTurn(@PathVariable String turnId, @RequestBody BusTurn updatedTurn) {
        return busTurnRepo.findById(turnId).map(existing -> {
            if (!busInfoRepo.existsById(updatedTurn.getBusId())) {
                return ResponseEntity.badRequest().body("Bus ID '" + updatedTurn.getBusId() + "' does not exist.");
            }

            updatedTurn.setTurnId(turnId); // Ensure the ID stays the same
            busTurnRepo.save(updatedTurn);
            return ResponseEntity.ok("Turn updated successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }

    //  Delete
    @DeleteMapping("/delete/{turnId}")
    public ResponseEntity<String> deleteTurn(@PathVariable String turnId) {
        if (busTurnRepo.existsById(turnId)) {
            busTurnRepo.deleteById(turnId);
            return ResponseEntity.ok("Turn deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
