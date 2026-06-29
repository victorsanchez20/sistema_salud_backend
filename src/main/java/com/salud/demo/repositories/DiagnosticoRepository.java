package com.salud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salud.demo.models.Diagnostico;


public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
 
    Optional<Diagnostico> findById(Long id);
}
