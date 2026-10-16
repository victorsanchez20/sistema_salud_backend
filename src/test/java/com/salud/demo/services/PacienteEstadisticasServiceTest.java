package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class PacienteEstadisticasServiceTest {

    @Mock private PacienteRepository pacienteRepository;

    private PacienteEstadisticasService service;

    @BeforeEach
    void setUp() {
        Clock relojFijo = Clock.fixed(Instant.parse("2026-10-10T12:00:00Z"), ZoneOffset.UTC);
        service = new PacienteEstadisticasService(pacienteRepository, relojFijo);
    }

    private Paciente nacidoEl(LocalDate nacimiento) {
        Paciente p = new Paciente();
        p.setNacimiento(nacimiento);
        return p;
    }

    @Test
    void agrupaPacientesPorRangoDeEdadConFechaFija() {
        when(pacienteRepository.findAll()).thenReturn(List.of(
            nacidoEl(LocalDate.of(2020, 1, 1)),   // 6 años  -> 0-10
            nacidoEl(LocalDate.of(2010, 1, 1)),   // 16 años -> 11-20
            nacidoEl(LocalDate.of(2000, 1, 1)),   // 26 años -> 21-30
            nacidoEl(LocalDate.of(1950, 1, 1)),   // 76 años -> 50+
            nacidoEl(null)));                     // sin fecha: se ignora

        Map<String, Integer> resultado = service.pacientesPorEdad();

        assertEquals(1, resultado.get("0-10"));
        assertEquals(1, resultado.get("11-20"));
        assertEquals(1, resultado.get("21-30"));
        assertEquals(0, resultado.get("31-40"));
        assertEquals(1, resultado.get("50+"));
        assertEquals(6, resultado.size());
    }
}
