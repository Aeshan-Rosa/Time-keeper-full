package com.example.bus.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreakdownRequest {
    private String plateNo;

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getBreakdownTime() {
        return breakdownTime;
    }

    public void setBreakdownTime(String breakdownTime) {
        this.breakdownTime = breakdownTime;
    }

    private String breakdownTime; // Format: "HH:mm:ss"
}

