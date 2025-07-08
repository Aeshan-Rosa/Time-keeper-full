package com.example.bus.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUS_TURN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusTurn {
    public String getTurnId() {
        return turnId;
    }

    public void setTurnId(String turnId) {
        this.turnId = turnId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    @Id
    @Column(name = "TURN_ID")
    private String turnId;

    @Column(name = "BUS_ID")
    private String busId;
}
