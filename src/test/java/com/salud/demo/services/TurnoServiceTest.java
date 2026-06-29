package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Turno;
import com.salud.demo.repositories.TurnoRepository;

@ExtendWith(MockitoExtension.class)
class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TurnoService turnoService;

    private Turno turno;

    @BeforeEach
    void setUp() {
        turno = new Turno(1L, "M", LocalTime.of(8, 0), LocalTime.of(12, 0));
    }

    @Test
    void getAllTurnos() {
        when(turnoRepository.findAll()).thenReturn(List.of(turno));

        var result = turnoService.getAllTurnos();

        assertEquals(1, result.size());
        assertEquals("M", result.get(0).getCodigo());
        verify(turnoRepository).findAll();
    }

    @Test
    void save() {
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

        var result = turnoService.save(turno);

        assertNotNull(result);
        assertEquals("M", result.getCodigo());
        verify(turnoRepository).save(turno);
    }

    @Test
    void deleteById() {
        turnoService.deleteById(1L);
        verify(turnoRepository).deleteById(1L);
    }
}
