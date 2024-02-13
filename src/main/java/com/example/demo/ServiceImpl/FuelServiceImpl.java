package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Fuel;
import com.example.demo.Respository.FuelRepository;
import com.example.demo.Service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelServiceImpl implements FuelService {

    private final FuelRepository fuelRepository;

    @Autowired
    public FuelServiceImpl(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @Override
    public Fuel saveFuel(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    @Override
    public Fuel getFuelById(Long fuelId) {
        return fuelRepository.findById(fuelId).orElse(null);
    }

    @Override
    public List<Fuel> getAllFuel() {
        return fuelRepository.findAll();
    }

    @Override
    public void deleteFuel(Long fuelId) {
        fuelRepository.deleteById(fuelId);
    }

    @Override
    public void updateFuel(Fuel fuel) {
        if (fuel.getId() != null) {
            Fuel existingFuel = fuelRepository.findById(fuel.getId()).orElse(null);

            if (existingFuel != null) {
                existingFuel.setRefuel_date(fuel.getRefuel_date());
                existingFuel.setLiters(fuel.getLiters());
                existingFuel.setCost(fuel.getCost());
                existingFuel.setMileage(fuel.getMileage());
                // Update other properties as needed

                fuelRepository.save(existingFuel);
            }
        }
    }
}
