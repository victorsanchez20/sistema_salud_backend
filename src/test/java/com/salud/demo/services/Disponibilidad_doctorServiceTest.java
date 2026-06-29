package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.CalendarioGuardadoDTO;
import com.salud.demo.models.DisponibilidadDTO;
import com.salud.demo.models.DisponibilidadViewDTO;
import com.salud.demo.models.Disponibilidad_doctor;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.Turno;
import com.salud.demo.repositories.Disponibilidad_doctorRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.TurnoRepository;

@ExtendWith(MockitoExtension.class)
class Disponibilidad_doctorServiceTest {

    @Mock
    private Disponibilidad_doctorRepository disponibilidad_doctorRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private Disponibilidad_doctorService service;

    @BeforeEach
    void injectRepoField() throws Exception {
        Field field = Disponibilidad_doctorService.class.getDeclaredField("disponibilidadRepo");
        field.setAccessible(true);
        field.set(service, disponibilidad_doctorRepository);
    }

    private Doctor doctor;
    private Turno turno;
    private Disponibilidad_doctor disponibilidad;

    @BeforeEach
    void setUp() {
        doctor = new Doctor(1L, "Dr. Perez", "Activo");
        turno = new Turno(1L, "M", LocalTime.of(8, 0), LocalTime.of(12, 0));
        disponibilidad = new Disponibilidad_doctor(1L, doctor, turno, LocalDate.of(2026, 6, 1));
    }

    @Test
    void getAllDisponibilidad() {
        when(disponibilidad_doctorRepository.findAll()).thenReturn(List.of(disponibilidad));

        var result = service.getAllDisponibilidad();

        assertEquals(1, result.size());
        verify(disponibilidad_doctorRepository).findAll();
    }

    @Test
    void save() {
        when(disponibilidad_doctorRepository.save(any(Disponibilidad_doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.save(disponibilidad);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(disponibilidad_doctorRepository).save(disponibilidad);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(disponibilidad_doctorRepository).deleteById(1L);
    }

    @Test
    void guardarMasivo() {
        DisponibilidadDTO dto = new DisponibilidadDTO(1L, 1L, LocalDate.of(2026, 6, 1));

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        service.guardarMasivo(List.of(dto));

        verify(doctorRepository).findById(1L);
        verify(turnoRepository).findById(1L);
        verify(disponibilidad_doctorRepository).save(any(Disponibilidad_doctor.class));
    }

    @Test
    void guardarMasivoDoctorNotFound() {
        DisponibilidadDTO dto = new DisponibilidadDTO(99L, 1L, LocalDate.of(2026, 6, 1));

        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardarMasivo(List.of(dto));
        });

        assertEquals("Doctor no encontrado", exception.getMessage());
        verify(disponibilidad_doctorRepository, never()).save(any());
    }

    @Test
    void guardarMasivoTurnoNotFound() {
        DisponibilidadDTO dto = new DisponibilidadDTO(1L, 99L, LocalDate.of(2026, 6, 1));

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(turnoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardarMasivo(List.of(dto));
        });

        assertEquals("Turno no encontrado", exception.getMessage());
        verify(disponibilidad_doctorRepository, never()).save(any());
    }

    @Test
    void listarPorMesAnio() {
        when(disponibilidad_doctorRepository.findByMesAndAnio(6, 2026)).thenReturn(List.of(disponibilidad));

        List<DisponibilidadViewDTO> result = service.listarPorMesAnio(6, 2026);

        assertEquals(1, result.size());
        assertEquals("Dr. Perez", result.get(0).getDoctor());
        assertEquals("M", result.get(0).getTurno());
        assertEquals(LocalDate.of(2026, 6, 1), result.get(0).getFecha());
    }

    @Test
    void obtenerCalendariosGuardados() {
        CalendarioGuardadoDTO cal = new CalendarioGuardadoDTO(6, 2026, 10);
        when(disponibilidad_doctorRepository.obtenerCalendariosGuardados()).thenReturn(List.of(cal));

        var result = service.obtenerCalendariosGuardados();

        assertEquals(1, result.size());
        assertEquals(6, result.get(0).getMes());
        assertEquals(2026, result.get(0).getAnio());
        assertEquals(10, result.get(0).getTotalTurnos());
    }

    @Test
    void eliminarPorMesAnio() {
        service.eliminarPorMesAnio(6, 2026);
        verify(disponibilidad_doctorRepository).deleteByMesAndAnio(6, 2026);
    }

    @Test
    void listarPorDoctor() {
        when(disponibilidad_doctorRepository.buscarPorDoctorId(1L)).thenReturn(List.of(disponibilidad));

        var result = service.listarPorDoctor(1L);

        assertEquals(1, result.size());
        verify(disponibilidad_doctorRepository).buscarPorDoctorId(1L);
    }

    @Test
    void fechasDisponibles() {
        when(disponibilidad_doctorRepository.findDisponibilidadFuturaPorDoctor(1L)).thenReturn(List.of(disponibilidad));

        List<LocalDate> result = service.fechasDisponibles(1L, LocalTime.of(9, 0));

        assertEquals(1, result.size());
        assertEquals(LocalDate.of(2026, 6, 1), result.get(0));
    }

    @Test
    void fechasDisponiblesOutOfRange() {
        when(disponibilidad_doctorRepository.findDisponibilidadFuturaPorDoctor(1L)).thenReturn(List.of(disponibilidad));

        List<LocalDate> result = service.fechasDisponibles(1L, LocalTime.of(13, 0));

        assertTrue(result.isEmpty());
    }

    @Test
    void cantidadPorFecha() {
        Disponibilidad_doctor d2 = new Disponibilidad_doctor(2L, doctor, turno, LocalDate.of(2026, 6, 1));
        when(disponibilidad_doctorRepository.findAll()).thenReturn(List.of(disponibilidad, d2));

        Map<String, Long> result = service.cantidadPorFecha();

        assertEquals(1, result.size());
        assertEquals(2L, result.get("2026-06-01"));
    }
}
