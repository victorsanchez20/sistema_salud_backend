package com.salud.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salud.demo.models.CalendarioGuardadoDTO;
import com.salud.demo.models.Disponibilidad_doctor;

@Repository
public interface Disponibilidad_doctorRepository extends JpaRepository<Disponibilidad_doctor, Long> {

    @Query("""
    SELECT d FROM Disponibilidad_doctor d
    WHERE MONTH(d.fecha) = :mes AND YEAR(d.fecha) = :anio
    """)
    List<Disponibilidad_doctor> findByMesAndAnio(
        @Param("mes") int mes,
        @Param("anio") int anio
    );

    @Query("""
    SELECT new com.salud.demo.models.CalendarioGuardadoDTO(
        MONTH(d.fecha),
        YEAR(d.fecha),
        COUNT(d)
    )
    FROM Disponibilidad_doctor d
    GROUP BY YEAR(d.fecha), MONTH(d.fecha)
    ORDER BY YEAR(d.fecha), MONTH(d.fecha)
    """)
    List<CalendarioGuardadoDTO> obtenerCalendariosGuardados();


    @Modifying
    @Query("""
        DELETE FROM Disponibilidad_doctor d
        WHERE MONTH(d.fecha) = :mes
        AND YEAR(d.fecha) = :anio
    """)
    void deleteByMesAndAnio(@Param("mes") int mes,
                            @Param("anio") int anio);

    // Cambia findByDoctor_Id por:
    @Query("SELECT d FROM Disponibilidad_doctor d WHERE d.id_doctor.id = :doctorId")
    List<Disponibilidad_doctor> buscarPorDoctorId(@Param("doctorId") Long doctorId);

    /*
    @Query("""
        SELECT DISTINCT d.fecha
        FROM Disponibilidad_doctor d
        WHERE d.idDoctor = :doctorId
          AND d.fecha >= CURRENT_DATE
        ORDER BY d.fecha
    """)
    List<LocalDate> findFechasTrabajoDoctor(
            @Param("doctorId") Long doctorId
    );*/

    @Query("""
    SELECT d FROM Disponibilidad_doctor d
    WHERE d.id_doctor.id = :doctorId
      AND d.fecha >= CURRENT_DATE
""")
List<Disponibilidad_doctor> findDisponibilidadFuturaPorDoctor(
        @Param("doctorId") Long doctorId
);

@Query("""
    SELECT d FROM Disponibilidad_doctor d
    WHERE d.id_doctor.id = :doctorId
      AND d.fecha = :fecha
""")
Optional<Disponibilidad_doctor> findByDoctorAndFecha(
        @Param("doctorId") Long doctorId,
        @Param("fecha") LocalDate fecha
);


}
