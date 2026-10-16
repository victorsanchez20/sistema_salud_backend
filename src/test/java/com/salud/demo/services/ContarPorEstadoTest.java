package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.repositories.SesionRepository;

@ExtendWith(MockitoExtension.class)
class ContarPorEstadoTest {

    @Mock private SesionRepository sesionRepository;

    @InjectMocks
    private SesionService sesionService;

    @Test
    void contarPorEstadoConsultaUnaVezCadaEstadoDelEnum() {
        when(sesionRepository.countByEstado(1)).thenReturn(4L);
        when(sesionRepository.countByEstado(2)).thenReturn(7L);
        when(sesionRepository.countByEstado(3)).thenReturn(1L);

        Map<String, Long> conteo = sesionService.contarPorEstado();

        assertEquals(4L, conteo.get("registrada"));
        assertEquals(7L, conteo.get("completada"));
        assertEquals(1L, conteo.get("cancelada"));
        assertEquals(3, conteo.size());
    }
}
