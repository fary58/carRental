package com.example.demo.Controller;

import com.example.demo.Entity.Fuel;
import com.example.demo.Service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    @Autowired
    private FuelService fuelService;

    @PostMapping("/saveFuel")
    public ResponseEntity<Fuel> saveFuel(@Valid @RequestBody Fuel fuel) {
        Fuel savedFuel = fuelService.saveFuel(fuel);
        return ResponseEntity.ok(savedFuel);
    }

    @GetMapping("/{fuelId}")
    public ResponseEntity<Fuel> getFuelById(@Valid @PathVariable Long fuelId) {
        Fuel fuel = fuelService.getFuelById(fuelId);
        if (fuel != null) {
            return ResponseEntity.ok(fuel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Fuel> getAllFuel() {
        return fuelService.getAllFuel();
    }

    @DeleteMapping("/{fuelId}")
    public ResponseEntity<Void> deleteFuel(@Valid @PathVariable Long fuelId) {
        fuelService.deleteFuel(fuelId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateFuel")
    public ResponseEntity<Fuel> updateFuel(@Valid @RequestBody Fuel fuel) {
        fuelService.updateFuel(fuel);
        return ResponseEntity.ok(fuel);
    }
}
