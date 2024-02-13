package com.example.demo.Respository;

import com.example.demo.Entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    // Additional query methods can be added if needed
}
