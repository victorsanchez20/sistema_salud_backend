package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente(1L, "Garcia", "Lopez", "Juan", "12345678", "HC001", "999888777", "Av. Siempre Viva", LocalDate.of(1990, 1, 1), "Peruana", LocalDate.now());
    }

    @Test
    void getAllPacientes() {
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> result = pacienteService.getAllPacientes();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        verify(pacienteRepository).findAll();
    }

    @Test
    void savePaciente() {
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente result = pacienteService.save(paciente);

        assertNotNull(result);
        assertEquals("HC001", result.getHc());
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void deleteById() {
        pacienteService.deleteById(1L);
        verify(pacienteRepository).deleteById(1L);
    }

    @Test
    void getByHcFound() {
        when(pacienteRepository.findByHc("HC001")).thenReturn(Optional.of(paciente));

        Paciente result = pacienteService.getByHc("HC001");

        assertNotNull(result);
        assertEquals("HC001", result.getHc());
        verify(pacienteRepository).findByHc("HC001");
    }

    @Test
    void getByHcNotFound() {
        when(pacienteRepository.findByHc("HC999")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.getByHc("HC999");
        });

        assertTrue(exception.getMessage().contains("no encontrado"));
        verify(pacienteRepository).findByHc("HC999");
    }

    @Test
    void getByDNIFound() {
        when(pacienteRepository.findByDni("12345678")).thenReturn(Optional.of(paciente));

        Paciente result = pacienteService.getByDNI("12345678");

        assertNotNull(result);
        assertEquals("12345678", result.getDni());
    }

    @Test
    void getByDNINotFound() {
        when(pacienteRepository.findByDni("00000000")).thenReturn(Optional.empty());

        Paciente result = pacienteService.getByDNI("00000000");

        assertNull(result);
    }

    @Test
    void buscarPaciente() {
        when(pacienteRepository.buscarPorNombreCompleto("Juan")).thenReturn(List.of(paciente));
        when(pacienteRepository.buscarPorNombreCompleto("Garcia")).thenReturn(List.of());

        List<Paciente> result = pacienteService.buscarPaciente("Juan Garcia");

        assertEquals(1, result.size());
        verify(pacienteRepository).buscarPorNombreCompleto("Juan");
        verify(pacienteRepository).buscarPorNombreCompleto("Garcia");
    }

    @Test
    void ultimaHcWithValue() {
        when(pacienteRepository.maxHc()).thenReturn(5);

        int result = pacienteService.ultimaHc();

        assertEquals(5, result);
    }

    @Test
    void ultimaHcNull() {
        when(pacienteRepository.maxHc()).thenReturn(null);

        int result = pacienteService.ultimaHc();

        assertEquals(0, result);
    }
}
