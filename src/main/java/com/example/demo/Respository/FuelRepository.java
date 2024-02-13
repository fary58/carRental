package com.example.demo.Respository;

import com.example.demo.Entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelRepository extends JpaRepository<Fuel, Long> {
    // Additional query methods can be added if needed
}
