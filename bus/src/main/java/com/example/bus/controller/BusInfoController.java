package com.example.bus.controller;

import com.example.bus.model.BusInfo;
import com.example.bus.repository.BusInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
@CrossOrigin("*")
public class BusInfoController {

    @Autowired
    private BusInfoRepository busInfoRepository;

    //  Get
    @GetMapping("/all")
    public ResponseEntity<List<BusInfo>> getAllBuses() {
        return ResponseEntity.ok(busInfoRepository.findAll());
    }

    //  Add
    @PostMapping("/add")
    public ResponseEntity<String> addBus(@RequestBody BusInfo newBus) {
        if (busInfoRepository.existsById(newBus.getBusId())) {
            return ResponseEntity.badRequest().body("Bus ID already exists.");
        }
        busInfoRepository.save(newBus);
        return ResponseEntity.ok("Bus added successfully.");
    }

    //  Update
    @PutMapping("/update/{busId}")
    public ResponseEntity<String> updateBus(@PathVariable String busId, @RequestBody BusInfo updatedBus) {
        return busInfoRepository.findById(busId).map(bus -> {
            bus.setPlateNo(updatedBus.getPlateNo());
            busInfoRepository.save(bus);
            return ResponseEntity.ok("Bus updated successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/delete/{busId}")
    public ResponseEntity<String> deleteBus(@PathVariable String busId) {
        if (busInfoRepository.existsById(busId)) {
            busInfoRepository.deleteById(busId);
            return ResponseEntity.ok("Bus deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
