package com.salud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salud.demo.models.HorarioDoctor;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


@Repository
public interface HorarioDoctorRepository extends JpaRepository<HorarioDoctor, Long> {

    void deleteByDoctorIdAndFecha(Long doctorId, LocalDate fecha);

    @Query("""
        SELECT h.horaInicio
        FROM HorarioDoctor h
        WHERE h.doctor.id = :doctorId
        ORDER BY h.horaInicio
    """)
    List<LocalTime> findHorasByDoctor(@Param("doctorId") Long doctorId);


    int countByDoctorIdAndFecha(Long doctorId, LocalDate fecha);

    List<HorarioDoctor> findByDoctorIdAndFecha(Long doctorId, LocalDate fecha);

    List<HorarioDoctor> findByDoctorId(Long doctorId);


    void deleteByDoctorIdAndHoraInicio(Long doctorId, LocalTime hora);

    @Query("""
    SELECT DISTINCT h.horaInicio
    FROM HorarioDoctor h
    WHERE h.doctor.id = :doctorId
    ORDER BY h.horaInicio
    """)
    List<LocalTime> findHorasBaseDoctor(@Param("doctorId") Long doctorId);

}   
