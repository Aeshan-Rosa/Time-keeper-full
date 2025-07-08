package com.example.bus.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Bus_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusInfo {
    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Id
    @Column(name = "BUS_ID")
    private String busId;

    @Column(name = "PLATE_NO")
    private String plateNo;
}

