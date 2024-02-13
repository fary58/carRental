package com.example.demo.Controller;

import com.example.demo.Entity.Maintenance;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Vehicle;
import com.example.demo.Service.MaintenanceService;
import com.example.demo.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/maintenance")

public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/saveMaintenance")
    public ResponseEntity<Object> saveMaintenance(@Valid @RequestBody Maintenance maintenance) {
        try {
            Maintenance savedMaintenance = maintenanceService.saveMaintenance(maintenance);
            return new ResponseEntity<>(savedMaintenance, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>("Error saving maintenance record", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{maintenanceId}")
    public ResponseEntity<Maintenance> getMaintenanceById(@PathVariable Long maintenanceId) {
        Maintenance maintenance = maintenanceService.getMaintenanceById(maintenanceId);
        if (maintenance != null) {
            return ResponseEntity.ok(maintenance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Maintenance> getAllMaintenance() {
        return maintenanceService.getAllMaintenance();
    }

    @DeleteMapping("/{maintenanceId}")
    public ResponseEntity<Void> deleteMaintenance(@Valid @PathVariable Long maintenanceId) {
        maintenanceService.deleteMaintenance(maintenanceId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateMaintenance")
    public ResponseEntity<Maintenance> updateMaintenance(@RequestBody Maintenance maintenance) {
        maintenanceService.updateMaintenance(maintenance);
        return ResponseEntity.ok(maintenance);
    }
}
