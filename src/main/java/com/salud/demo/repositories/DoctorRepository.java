package com.salud.demo.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.salud.demo.models.Doctor;





public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByNombre(String nombre);
}
