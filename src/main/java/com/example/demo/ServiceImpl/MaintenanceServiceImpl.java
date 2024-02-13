package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Maintenance;
import com.example.demo.Respository.MaintenanceRepository;
import com.example.demo.Service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Autowired
    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public Maintenance saveMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance getMaintenanceById(Long maintenanceId) {
        return maintenanceRepository.findById(maintenanceId).orElse(null);
    }

    @Override
    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }

    @Override
    public void deleteMaintenance(Long maintenanceId) {
        maintenanceRepository.deleteById(maintenanceId);
    }

    @Override
    public void updateMaintenance(Maintenance maintenance) {
        if (maintenance.getId() != null) {
            Maintenance existingMaintenance = maintenanceRepository.findById(maintenance.getId()).orElse(null);

            if (existingMaintenance != null) {
                existingMaintenance.setActivity(maintenance.getActivity());
                existingMaintenance.setScheduled_date(maintenance.getScheduled_date());
                existingMaintenance.setActual_date(maintenance.getActual_date());
                existingMaintenance.setDescription(maintenance.getDescription());
                existingMaintenance.setVehicle(maintenance.getVehicle());
                // Update other properties as needed

                maintenanceRepository.save(existingMaintenance);
            }
        }
    }
}


