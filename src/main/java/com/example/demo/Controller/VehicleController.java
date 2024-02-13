package com.example.demo.Controller;

import com.example.demo.Entity.Vehicle;
import com.example.demo.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/crete_Vechile")
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@Valid @PathVariable Long vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle != null) {
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_allVechiles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

//    @DeleteMapping("/{vehicleId}")
//    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
//        vehicleService.deleteVehicle(vehicleId);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.noContent().build();
    }

//        @PutMapping("/updateVehicle/{userId}")
//    public ResponseEntity<Vehicle> updateVehicleForUser(
//            @PathVariable Long userId,
//            @Valid @RequestBody Vehicle updatedVehicle) {
//
//        // Check if the user exists
//        User user = userService.getUserById(userId);
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Check if the vehicle exists
//        Long vehicleId = updatedVehicle.getId();
//        Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);
//        if (existingVehicle == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Ensure the existing vehicle belongs to the specified user
//        if (!existingVehicle.getUser().getId().equals(userId)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        // Update the vehicle details
//        existingVehicle.setBrand(updatedVehicle.getBrand());
//        existingVehicle.setModel(updatedVehicle.getModel());
//        // Update other vehicle details as needed
//
//        // Save the updated vehicle
//        Vehicle savedVehicle = vehicleService.saveVehicle(existingVehicle);
//
//        return new ResponseEntity<>(savedVehicle, HttpStatus.OK);
//    }
    // Additional methods and endpoints as needed
}


