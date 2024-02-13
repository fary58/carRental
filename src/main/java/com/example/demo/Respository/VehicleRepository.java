package com.example.demo.Respository;

import com.example.demo.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
