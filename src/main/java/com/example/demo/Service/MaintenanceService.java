package com.example.demo.Service;

import com.example.demo.Entity.Maintenance;

import java.util.List;

public interface MaintenanceService {
    Maintenance saveMaintenance(Maintenance maintenance);

    Maintenance getMaintenanceById(Long maintenanceId);

    List<Maintenance> getAllMaintenance();

    void deleteMaintenance(Long maintenanceId);

    void updateMaintenance(Maintenance maintenance);
}
