package com.example.demo.Service;

import com.example.demo.Entity.Fuel;

import java.util.List;

public interface FuelService {
    Fuel saveFuel(Fuel fuel);

    Fuel getFuelById(Long fuelId);

    List<Fuel> getAllFuel();

    void deleteFuel(Long fuelId);

    void updateFuel(Fuel fuel);
}
