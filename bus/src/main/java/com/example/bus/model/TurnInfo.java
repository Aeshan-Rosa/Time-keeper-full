package com.example.bus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "TURN_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnInfo {
    @Id
    @Column(name = "TURN_ID")
    private String turnId;

    public String getTurnId() {
        return turnId;
    }

    public void setTurnId(String turnId) {
        this.turnId = turnId;
    }

    public LocalTime getTrip01Arrival() {
        return trip01Arrival;
    }

    public void setTrip01Arrival(LocalTime trip01Arrival) {
        this.trip01Arrival = trip01Arrival;
    }

    public LocalTime getTrip01Departure() {
        return trip01Departure;
    }

    public void setTrip01Departure(LocalTime trip01Departure) {
        this.trip01Departure = trip01Departure;
    }

    public LocalTime getTrip02Arrival() {
        return trip02Arrival;
    }

    public void setTrip02Arrival(LocalTime trip02Arrival) {
        this.trip02Arrival = trip02Arrival;
    }

    public LocalTime getTrip02Departure() {
        return trip02Departure;
    }

    public void setTrip02Departure(LocalTime trip02Departure) {
        this.trip02Departure = trip02Departure;
    }

    public LocalTime getTrip03Arrival() {
        return trip03Arrival;
    }

    public void setTrip03Arrival(LocalTime trip03Arrival) {
        this.trip03Arrival = trip03Arrival;
    }

    public LocalTime getTrip03Departure() {
        return trip03Departure;
    }

    public void setTrip03Departure(LocalTime trip03Departure) {
        this.trip03Departure = trip03Departure;
    }

    public LocalTime getTrip04Arrival() {
        return trip04Arrival;
    }

    public void setTrip04Arrival(LocalTime trip04Arrival) {
        this.trip04Arrival = trip04Arrival;
    }

    public LocalTime getTrip04Departure() {
        return trip04Departure;
    }

    public void setTrip04Departure(LocalTime trip04Departure) {
        this.trip04Departure = trip04Departure;
    }

    public LocalTime getTrip05Arrival() {
        return trip05Arrival;
    }

    public void setTrip05Arrival(LocalTime trip05Arrival) {
        this.trip05Arrival = trip05Arrival;
    }

    public LocalTime getTrip05Departure() {
        return trip05Departure;
    }

    public void setTrip05Departure(LocalTime trip05Departure) {
        this.trip05Departure = trip05Departure;
    }

    public LocalTime getTrip06Arrival() {
        return trip06Arrival;
    }

    public void setTrip06Arrival(LocalTime trip06Arrival) {
        this.trip06Arrival = trip06Arrival;
    }

    public LocalTime getTrip06Departure() {
        return trip06Departure;
    }

    public void setTrip06Departure(LocalTime trip06Departure) {
        this.trip06Departure = trip06Departure;
    }

    public LocalTime getTrip07Arrival() {
        return trip07Arrival;
    }

    public void setTrip07Arrival(LocalTime trip07Arrival) {
        this.trip07Arrival = trip07Arrival;
    }

    public LocalTime getTrip07Departure() {
        return trip07Departure;
    }

    public void setTrip07Departure(LocalTime trip07Departure) {
        this.trip07Departure = trip07Departure;
    }

    public LocalTime getTrip08Arrival() {
        return trip08Arrival;
    }

    public void setTrip08Arrival(LocalTime trip08Arrival) {
        this.trip08Arrival = trip08Arrival;
    }

    public LocalTime getTrip08Departure() {
        return trip08Departure;
    }

    public void setTrip08Departure(LocalTime trip08Departure) {
        this.trip08Departure = trip08Departure;
    }

    public LocalTime getTrip09Arrival() {
        return trip09Arrival;
    }

    public void setTrip09Arrival(LocalTime trip09Arrival) {
        this.trip09Arrival = trip09Arrival;
    }

    public LocalTime getTrip09Departure() {
        return trip09Departure;
    }

    public void setTrip09Departure(LocalTime trip09Departure) {
        this.trip09Departure = trip09Departure;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Column(name = "TRIP_01_ARRIVAL") private LocalTime trip01Arrival;
    @Column(name = "TRIP_01_DEPARTURE") private LocalTime trip01Departure;
    @Column(name = "TRIP_02_ARRIVAL") private LocalTime trip02Arrival;
    @Column(name = "TRIP_02_DEPARTURE") private LocalTime trip02Departure;
    @Column(name = "TRIP_03_ARRIVAL") private LocalTime trip03Arrival;
    @Column(name = "TRIP_03_DEPARTURE") private LocalTime trip03Departure;
    @Column(name = "TRIP_04_ARRIVAL") private LocalTime trip04Arrival;
    @Column(name = "TRIP_04_DEPARTURE") private LocalTime trip04Departure;
    @Column(name = "TRIP_05_ARRIVAL") private LocalTime trip05Arrival;
    @Column(name = "TRIP_05_DEPARTURE") private LocalTime trip05Departure;
    @Column(name = "TRIP_06_ARRIVAL") private LocalTime trip06Arrival;
    @Column(name = "TRIP_06_DEPARTURE") private LocalTime trip06Departure;
    @Column(name = "TRIP_07_ARRIVAL") private LocalTime trip07Arrival;
    @Column(name = "TRIP_07_DEPARTURE") private LocalTime trip07Departure;
    @Column(name = "TRIP_08_ARRIVAL") private LocalTime trip08Arrival;
    @Column(name = "TRIP_08_DEPARTURE") private LocalTime trip08Departure;
    @Column(name = "TRIP_09_ARRIVAL") private LocalTime trip09Arrival;
    @Column(name = "TRIP_09_DEPARTURE") private LocalTime trip09Departure;
    @Column(name = "END") private LocalTime end;
}

