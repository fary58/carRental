package com.example.demo.Respository;

import com.example.demo.Entity.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;





public interface DispatchRepository extends JpaRepository<Dispatch, Long> {
    // Additional query methods can be added if needed
}
