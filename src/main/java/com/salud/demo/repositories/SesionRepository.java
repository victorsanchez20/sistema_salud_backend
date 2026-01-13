package com.salud.demo.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salud.demo.models.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
 
    List<Sesion> findByCita_Id(Long idCita);               // ✅ funciona
    List<Sesion> findByCita_Paciente_Id(Long idPaciente); // ✅ funciona

    boolean existsByFecha(LocalDateTime fecha);

    @Query("""
    SELECT s.fecha
    FROM Sesion s
    WHERE DATE(s.fecha) = :fecha
        AND s.cita.doctor.id = :doctorId
    """)
    List<LocalDateTime> findHorasOcupadas(
        @Param("fecha") LocalDate fecha,
        @Param("doctorId") Long doctorId
    );
    
    // 1️⃣ sesión más cercana desde ahora
    @Query("""
        SELECT s FROM Sesion s
        WHERE s.fecha >= CURRENT_TIMESTAMP
        ORDER BY s.fecha ASC
    """)
    List<Sesion> findProximaSesion(Pageable pageable);

    // 2️⃣ todas las sesiones de un día
    @Query("""
        SELECT s FROM Sesion s
        WHERE DATE(s.fecha) = :fecha
        ORDER BY s.fecha ASC
    """)
    List<Sesion> findSesionesPorFecha(@Param("fecha") LocalDate fecha);
}
