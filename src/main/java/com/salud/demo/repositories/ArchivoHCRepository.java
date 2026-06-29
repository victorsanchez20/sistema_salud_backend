package com.salud.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.salud.demo.models.ArchivoHC;


public interface ArchivoHCRepository extends JpaRepository<ArchivoHC, Long> {
    List<ArchivoHC> findByHistoriaClinicaId(Long historiaClinicaId);
}
