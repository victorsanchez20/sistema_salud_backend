package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Doctor;
import com.salud.demo.repositories.CitaRepository;
import com.salud.demo.repositories.DoctorRepository;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor(1L, "Dr. Perez", "Activo");
    }

    @Test
    void getAllDoctor() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));

        List<Doctor> result = doctorService.getAllDoctor();

        assertEquals(1, result.size());
        assertEquals("Dr. Perez", result.get(0).getNombre());
        verify(doctorRepository).findAll();
    }

    @Test
    void save() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor result = doctorService.save(doctor);

        assertNotNull(result);
        assertEquals("Dr. Perez", result.getNombre());
        verify(doctorRepository).save(doctor);
    }

    @Test
    void deleteById() {
        doctorService.deleteById(1L);
        verify(doctorRepository).deleteById(1L);
    }

    @Test
    void obtenerDoctorConPacientesSuccess() {
        when(doctorRepository.findByNombre("Dr. Perez")).thenReturn(Optional.of(doctor));
        when(citaRepository.findPacientesByDoctorId(1L)).thenReturn(List.of());

        Map<String, Object> result = doctorService.obtenerDoctorConPacientes("Dr. Perez");

        assertNotNull(result);
        assertEquals(doctor, result.get("doctor"));
        assertNotNull(result.get("pacientes"));
        verify(doctorRepository).findByNombre("Dr. Perez");
        verify(citaRepository).findPacientesByDoctorId(1L);
    }

    @Test
    void obtenerDoctorConPacientesNotFound() {
        when(doctorRepository.findByNombre("Unknown")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.obtenerDoctorConPacientes("Unknown");
        });

        assertEquals("Doctor no encontrado", exception.getMessage());
        verify(citaRepository, never()).findPacientesByDoctorId(anyLong());
    }
}
