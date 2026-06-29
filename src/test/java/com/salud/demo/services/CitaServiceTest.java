package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Cita;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.CitaRepository;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaService citaService;

    private Cita cita;

    @BeforeEach
    void setUp() {
        cita = new Cita();
        cita.setId(1L);
    }

    @Test
    void getAllCitas() {
        when(citaRepository.findAll()).thenReturn(List.of(cita));

        List<Cita> result = citaService.getAllCitas();

        assertEquals(1, result.size());
        verify(citaRepository).findAll();
    }

    @Test
    void save() {
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita result = citaService.save(cita);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(citaRepository).save(cita);
    }

    @Test
    void deletebyid() {
        citaService.deletebyid(1L);
        verify(citaRepository).deleteById(1L);
    }

    @Test
    void listarPorPaciente() {
        when(citaRepository.findByPacienteId(1L)).thenReturn(List.of(cita));

        List<Cita> result = citaService.listarPorPaciente(1L);

        assertEquals(1, result.size());
        verify(citaRepository).findByPacienteId(1L);
    }
}
