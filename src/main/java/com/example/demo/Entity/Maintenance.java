package com.example.demo.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activity;
    private LocalDateTime scheduled_date;
    private LocalDateTime actual_date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getScheduled_date() {
        return scheduled_date;
    }

    public void setScheduled_date(LocalDateTime scheduled_date) {
        this.scheduled_date = scheduled_date;
    }

    public LocalDateTime getActual_date() {
        return actual_date;
    }

    public void setActual_date(LocalDateTime actual_date) {
        this.actual_date = actual_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

