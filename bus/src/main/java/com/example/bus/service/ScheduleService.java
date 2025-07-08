package com.example.bus.service;

import com.example.bus.model.*;
import com.example.bus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    private BusInfoRepository busInfoRepo;

    @Autowired
    private BusTurnRepository busTurnRepo;

    @Autowired
    private TurnInfoRepository turnInfoRepo;

    public String handleBreakdown(BreakdownRequest request) {
        String plateNo = request.getPlateNo();
        LocalTime breakdownTime = LocalTime.parse(request.getBreakdownTime());

        BusInfo bus = busInfoRepo.findByPlateNo(plateNo)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        BusTurn turn = busTurnRepo.findByBusId(bus.getBusId())
                .orElseThrow(() -> new RuntimeException("Turn ID not found"));

        String turnId = turn.getTurnId();

        // Get all turns (not just same prefix anymore)
        List<TurnInfo> allTurns = turnInfoRepo.findAll();

        // Find the broken turn
        Optional<TurnInfo> brokenTurnOpt = allTurns.stream()
                .filter(t -> t.getTurnId().equals(turnId))
                .findFirst();

        if (brokenTurnOpt.isEmpty()) {
            throw new RuntimeException("Turn schedule not found");
        }

        TurnInfo broken = brokenTurnOpt.get();

        // Find first affected trip index
        int firstAffectedIndex = -1;
        for (int i = 1; i <= 9; i++) {
            LocalTime dep = getTime(broken, "getTrip0" + i + "Departure");
            if (dep != null && dep.isAfter(breakdownTime)) {
                firstAffectedIndex = i;
                break;
            }
        }

        if (firstAffectedIndex == -1) {
            return "No trips affected.";
        }

        System.out.println("--- Breakdown occurred on: " + plateNo + " (TURN_ID: " + turnId + ")");
        System.out.println("Breakdown Time: " + breakdownTime);
        System.out.println("Affected trips start from: TRIP_" + String.format("%02d", firstAffectedIndex));
        System.out.println("Rescheduling all buses (not saved to DB):");

        LocalTime endOfDay = LocalTime.of(22, 0);
        int totalMinutesLeft = endOfDay.toSecondOfDay() / 60 - breakdownTime.toSecondOfDay() / 60;

        for (TurnInfo ti : allTurns) {
            if (ti.getTurnId().equals(turnId)) continue;

            System.out.println("TURN_ID: " + ti.getTurnId());

            List<LocalTime> originalTimes = new ArrayList<>();
            for (int i = firstAffectedIndex; i <= 9; i++) {
                LocalTime t = getTime(ti, "getTrip0" + i + "Departure");
                if (t != null) {
                    originalTimes.add(t);
                }
            }

            if (originalTimes.size() < 2) continue;
            int tripCount = originalTimes.size();

            // Determine weight multiplier based on breakdown time
            double weightMultiplier;
            if (breakdownTime.isAfter(LocalTime.of(5, 0)) && breakdownTime.isBefore(LocalTime.of(8, 0))) {
                weightMultiplier = 1.5;
            } else if (breakdownTime.isAfter(LocalTime.of(10, 30)) && breakdownTime.isBefore(LocalTime.of(14, 30))) {
                weightMultiplier = 0.5;
            } else if (breakdownTime.isAfter(LocalTime.of(16, 0)) && breakdownTime.isBefore(LocalTime.of(19, 0))) {
                weightMultiplier = 2.0;
            } else if (breakdownTime.isAfter(LocalTime.of(20, 0)) && breakdownTime.isBefore(LocalTime.of(23, 0))) {
                weightMultiplier = 0.8;
            } else {
                weightMultiplier = 1.0;
            }

            // Use totalMinutesLeft but limit per turn (e.g. 30 mins)
            int baseAdjustment = Math.min(totalMinutesLeft, 30);
            int totalAdjustment = (int) Math.round(baseAdjustment * weightMultiplier);

            // Generate weighted intervals
            int[] rawWeights = new int[tripCount];
            int weightSum = 0;
            for (int i = 0; i < tripCount; i++) {
                rawWeights[i] = tripCount - i;
                weightSum += rawWeights[i];
            }

            int[] intervals = new int[tripCount];
            int totalAssigned = 0;
            for (int i = 0; i < tripCount; i++) {
                intervals[i] = (int) Math.round((double) rawWeights[i] * totalAdjustment / weightSum);
                totalAssigned += intervals[i];
            }

            int leftover = totalAdjustment - totalAssigned;
            int k = 0;
            while (leftover != 0) {
                intervals[k % tripCount] += leftover > 0 ? 1 : -1;
                leftover += leftover > 0 ? -1 : 1;
                k++;
            }

            // Build final schedule with 4-minute clash avoidance
            Set<LocalTime> scheduledTimes = new HashSet<>();
            for (TurnInfo t : allTurns) {
                for (int j = 1; j <= 9; j++) {
                    LocalTime tDep = getTime(t, "getTrip0" + j + "Departure");
                    if (tDep != null) scheduledTimes.add(tDep);
                }
            }

            for (int i = 0; i < tripCount; i++) {
                LocalTime original = originalTimes.get(i);
                LocalTime adjusted = original.minusMinutes(intervals[i]);

                while (!isGapOk(scheduledTimes, adjusted, 4)) {
                    adjusted = adjusted.minusMinutes(1);
                }

                scheduledTimes.add(adjusted);

                String tripNum = String.format("%02d", firstAffectedIndex + i);
                System.out.println("  TRIP_" + tripNum + "_DEPARTURE: " + original + " → " + adjusted +
                        "  (-" + (original.toSecondOfDay() - adjusted.toSecondOfDay()) / 60 + " min)");
            }
        }

        return "Breakdown processed. Full rescheduling printed to console.";
    }

    private boolean isGapOk(Set<LocalTime> times, LocalTime candidate, int minGap) {
        for (LocalTime t : times) {
            if (Math.abs(t.toSecondOfDay() - candidate.toSecondOfDay()) < minGap * 60) {
                return false;
            }
        }
        return true;
    }

    private LocalTime getTime(TurnInfo obj, String methodName) {
        try {
            Method m = TurnInfo.class.getMethod(methodName);
            return (LocalTime) m.invoke(obj);
        } catch (Exception e) {
            System.out.println("Failed to get time for method " + methodName + ": " + e.getMessage());
            return null;
        }
    }

    private void setTime(TurnInfo obj, String methodName, LocalTime value) {
        try {
            Method m = TurnInfo.class.getMethod(methodName, LocalTime.class);
            m.invoke(obj, value);
        } catch (Exception e) {
            System.out.println("Failed to set time for method " + methodName + ": " + e.getMessage());
        }
    }
}
