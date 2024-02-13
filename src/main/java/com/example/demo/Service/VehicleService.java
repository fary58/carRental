package com.example.demo.Service;

import com.example.demo.Entity.Vehicle;

import java.util.List;
import java.util.Vector;

public interface VehicleService {
    Vehicle saveVehicle(Vehicle vehicle);
    Vehicle getVehicleById(Long vehicleId);
    List<Vehicle> getAllVehicles();
    List<Vehicle> saveAllVehicles(List<Vehicle> vehicles);
    void deleteVehicle(Long id);


    // Additional methods as needed
}
