package com.salud.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salud.demo.models.Cita;
import com.salud.demo.models.Paciente;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteId(Long idPaciente);

    @Query("""
    SELECT p
    FROM Cita c
    JOIN c.paciente p
    WHERE c.doctor.id = :doctorId
    """)
    List<Paciente> findPacientesByDoctorId(Long doctorId);

    

}   
