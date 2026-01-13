package com.salud.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salud.demo.models.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findById(Long id);
    Optional<Paciente> findByHc(String hc);
    Optional<Paciente> findByDni(String dni);
    
    @Query("""
            SELECT p FROM Paciente p
            WHERE
            LOWER(p.apaterno) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(p.amaterno) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
            """)
            List<Paciente> buscarPorNombreCompleto(@Param("texto") String texto);
            
}
