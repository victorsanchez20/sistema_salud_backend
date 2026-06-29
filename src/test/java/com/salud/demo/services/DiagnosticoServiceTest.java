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

import com.salud.demo.models.Diagnostico;
import com.salud.demo.repositories.DiagnosticoRepository;

@ExtendWith(MockitoExtension.class)
class DiagnosticoServiceTest {

    @Mock
    private DiagnosticoRepository diagnosticoRepository;

    @InjectMocks
    private DiagnosticoService diagnosticoService;

    private Diagnostico diagnostico;

    @BeforeEach
    void setUp() {
        diagnostico = new Diagnostico(1L, "Hipertension", "Presion arterial elevada");
    }

    @Test
    void getAllDiagnosticos() {
        when(diagnosticoRepository.findAll()).thenReturn(List.of(diagnostico));

        var result = diagnosticoService.getAllDiagnosticos();

        assertEquals(1, result.size());
        assertEquals("Hipertension", result.get(0).getNombre());
        verify(diagnosticoRepository).findAll();
    }

    @Test
    void save() {
        when(diagnosticoRepository.save(any(Diagnostico.class))).thenReturn(diagnostico);

        var result = diagnosticoService.save(diagnostico);

        assertNotNull(result);
        assertEquals("Hipertension", result.getNombre());
        verify(diagnosticoRepository).save(diagnostico);
    }

    @Test
    void deleteById() {
        diagnosticoService.deleteById(1L);
        verify(diagnosticoRepository).deleteById(1L);
    }
}
