package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicle_id;

    private String vehicle_number;
    private String make;
    private String model;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "vehicle")
    private List<Fuel> fuelRecords = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<Maintenance> maintenanceActivities = new ArrayList<>();

    // Constructors, getters, and setters

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Fuel> getFuelRecords() {
        return fuelRecords;
    }

    public void setFuelRecords(List<Fuel> fuelRecords) {
        this.fuelRecords = fuelRecords;
    }

    public List<Maintenance> getMaintenanceActivities() {
        return maintenanceActivities;
    }

    public void setMaintenanceActivities(List<Maintenance> maintenanceActivities) {
        this.maintenanceActivities = maintenanceActivities;
    }
}



