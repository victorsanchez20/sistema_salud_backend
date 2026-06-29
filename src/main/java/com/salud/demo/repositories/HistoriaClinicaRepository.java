package com.salud.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salud.demo.models.HistoriaClinica;


public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    List<HistoriaClinica> findByPacienteId(Long pacienteId);
}
