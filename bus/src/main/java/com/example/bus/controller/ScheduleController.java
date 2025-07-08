package com.example.bus.controller;


import com.example.bus.model.BreakdownRequest;
import com.example.bus.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin("*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/breakdown")
    public ResponseEntity<String> handleBreakdown(@RequestBody BreakdownRequest request) {
        String result = scheduleService.handleBreakdown(request);
        return ResponseEntity.ok(result);
    }
}

