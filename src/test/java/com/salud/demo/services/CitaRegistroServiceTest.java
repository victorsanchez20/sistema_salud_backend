package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.exception.RecursoNoEncontradoException;
import com.salud.demo.models.Cita;
import com.salud.demo.models.CitaRequestDTO;
import com.salud.demo.models.Diagnostico;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.Paciente;
import com.salud.demo.models.Sesion;
import com.salud.demo.models.SesionDTO;
import com.salud.demo.repositories.CitaRepository;
import com.salud.demo.repositories.DiagnosticoRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.PacienteRepository;
import com.salud.demo.repositories.SesionRepository;

/** Pruebas de la lógica que se movió de CitaController a CitaService (SRP). */
@ExtendWith(MockitoExtension.class)
class CitaRegistroServiceTest {

    @Mock private CitaRepository citaRepository;
    @Mock private PacienteRepository pacienteRepository;
    @Mock private DoctorRepository doctorRepository;
    @Mock private DiagnosticoRepository diagnosticoRepository;
    @Mock private SesionRepository sesionRepository;

    @InjectMocks
    private CitaService citaService;

    private CitaRequestDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CitaRequestDTO(1L, 2L, LocalDate.of(2026, 10, 1), 2, List.of(
            new SesionDTO(1L, "2026-10-05 08:00:00", 1),
            new SesionDTO(2L, "2026-10-07 09:00:00", 1)));
    }

    @Test
    void registrarCitaGuardaCitaYUnaSesionPorCadaItem() {
        Paciente paciente = new Paciente();
        Doctor doctor = new Doctor(2L, "Dr. Perez", "Activo");
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(citaRepository.save(any(Cita.class))).thenAnswer(inv -> inv.getArgument(0));

        Cita resultado = citaService.registrarCita(dto);

        assertSame(paciente, resultado.getId_paciente());
        assertSame(doctor, resultado.getId_doctor());
        assertEquals(2, resultado.getNumero_cita());

        ArgumentCaptor<Sesion> captor = ArgumentCaptor.forClass(Sesion.class);
        verify(sesionRepository, times(2)).save(captor.capture());
        assertEquals(LocalDateTime.of(2026, 10, 5, 8, 0), captor.getAllValues().get(0).getFecha());
        assertEquals(LocalDateTime.of(2026, 10, 7, 9, 0), captor.getAllValues().get(1).getFecha());
    }

    @Test
    void registrarCitaFallaSiElPacienteNoExiste() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> citaService.registrarCita(dto));

        verify(citaRepository, never()).save(any());
        verify(sesionRepository, never()).save(any());
    }

    @Test
    void asignarDiagnosticoActualizaLaCita() {
        Cita cita = new Cita();
        Diagnostico diagnostico = new Diagnostico();
        when(citaRepository.findById(5L)).thenReturn(Optional.of(cita));
        when(diagnosticoRepository.findById(9L)).thenReturn(Optional.of(diagnostico));

        citaService.asignarDiagnostico(5L, 9L);

        assertSame(diagnostico, cita.getId_diagnostico());
        verify(citaRepository).save(cita);
    }
}
