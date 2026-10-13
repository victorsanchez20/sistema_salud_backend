package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.exception.RecursoNoEncontradoException;
import com.salud.demo.models.Disponibilidad_doctor;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.HorarioDoctor;
import com.salud.demo.models.Turno;
import com.salud.demo.repositories.Disponibilidad_doctorRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.HorarioDoctorRepository;

/** Pruebas de la lógica que se movió de HorarioDoctorController a HorarioDoctorService (SRP). */
@ExtendWith(MockitoExtension.class)
class HorarioDisponibilidadServiceTest {

    private static final LocalDate FECHA = LocalDate.of(2026, 10, 12);

    @Mock private HorarioDoctorRepository horarioRepo;
    @Mock private DoctorRepository doctorRepo;
    @Mock private Disponibilidad_doctorRepository disponibilidadRepo;

    @InjectMocks
    private HorarioDoctorService service;

    private Disponibilidad_doctor disponibilidad(LocalTime inicio, LocalTime fin) {
        Doctor doctor = new Doctor(1L, "Dr. Perez", "Activo");
        Turno turno = new Turno(1L, "M1", inicio, fin);
        return new Disponibilidad_doctor(1L, doctor, turno, FECHA);
    }

    @Test
    void horasDisponiblesExcluyeLasHorasOcupadas() {
        when(disponibilidadRepo.findByDoctorAndFecha(1L, FECHA))
            .thenReturn(Optional.of(disponibilidad(LocalTime.of(8, 0), LocalTime.of(11, 0))));
        HorarioDoctor ocupado = new HorarioDoctor();
        ocupado.setHoraInicio(LocalTime.of(9, 0));
        when(horarioRepo.findByDoctorIdAndFecha(1L, FECHA)).thenReturn(List.of(ocupado));

        assertEquals(List.of("08:00", "10:00"), service.horasDisponibles(1L, FECHA));
    }

    @Test
    void horasDisponiblesFallaSiElDoctorNoAtiendeEseDia() {
        when(disponibilidadRepo.findByDoctorAndFecha(1L, FECHA)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.horasDisponibles(1L, FECHA));
    }

    @Test
    void fechasDisponiblesSoloIncluyeDiasConHorasLibres() {
        when(disponibilidadRepo.findDisponibilidadFuturaPorDoctor(1L))
            .thenReturn(List.of(disponibilidad(LocalTime.of(8, 0), LocalTime.of(10, 0))));
        when(horarioRepo.countByDoctorIdAndFecha(1L, FECHA)).thenReturn(1);

        assertEquals(List.of(FECHA), service.fechasDisponibles(1L));
    }

    @Test
    void fechasDisponiblesExcluyeDiasCompletos() {
        when(disponibilidadRepo.findDisponibilidadFuturaPorDoctor(1L))
            .thenReturn(List.of(disponibilidad(LocalTime.of(8, 0), LocalTime.of(10, 0))));
        when(horarioRepo.countByDoctorIdAndFecha(1L, FECHA)).thenReturn(2);

        assertTrue(service.fechasDisponibles(1L).isEmpty());
    }
}
