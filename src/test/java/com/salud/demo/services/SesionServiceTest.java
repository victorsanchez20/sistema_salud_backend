package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.salud.demo.models.Sesion;
import com.salud.demo.repositories.SesionRepository;

@ExtendWith(MockitoExtension.class)
class SesionServiceTest {

    @Mock
    private SesionRepository sesionRepository;

    @InjectMocks
    private SesionService sesionService;

    private Sesion sesion;

    @BeforeEach
    void setUp() {
        sesion = new Sesion();
        sesion.setId(1L);
        sesion.setFecha(LocalDateTime.now());
        sesion.setEstado(1);
    }

    @Test
    void getAllSesions() {
        when(sesionRepository.findAll()).thenReturn(List.of(sesion));

        var result = sesionService.getAllSesions();

        assertEquals(1, result.size());
        verify(sesionRepository).findAll();
    }

    @Test
    void saveSuccess() {
        when(sesionRepository.existsByFecha(any(LocalDateTime.class))).thenReturn(false);
        when(sesionRepository.save(any(Sesion.class))).thenReturn(sesion);

        Sesion result = sesionService.save(sesion);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(sesionRepository).existsByFecha(sesion.getFecha());
        verify(sesionRepository).save(sesion);
    }

    @Test
    void saveConflict() {
        when(sesionRepository.existsByFecha(any(LocalDateTime.class))).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            sesionService.save(sesion);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("La hora seleccionada ya está ocupada", exception.getReason());
        verify(sesionRepository, never()).save(any());
    }

    @Test
    void deleteById() {
        sesionService.deleteById(1L);
        verify(sesionRepository).deleteById(1L);
    }

    @Test
    void getSesionesPorPaciente() {
        when(sesionRepository.findByCita_Paciente_Id(1L)).thenReturn(List.of(sesion));

        var result = sesionService.getSesionesPorPaciente(1L);

        assertEquals(1, result.size());
        verify(sesionRepository).findByCita_Paciente_Id(1L);
    }

    @Test
    void getSesionesPorCita() {
        when(sesionRepository.findByCita_Id(1L)).thenReturn(List.of(sesion));

        var result = sesionService.getSesionesPorCita(1L);

        assertEquals(1, result.size());
        verify(sesionRepository).findByCita_Id(1L);
    }

    @Test
    void obtenerSesionesDelDiaMasCercano() {
        Sesion proxima = new Sesion();
        proxima.setFecha(LocalDateTime.now().plusDays(1));
        when(sesionRepository.findProximaSesion(PageRequest.of(0, 1))).thenReturn(List.of(proxima));
        when(sesionRepository.findSesionesPorFecha(proxima.getFecha().toLocalDate())).thenReturn(List.of(proxima));

        var result = sesionService.obtenerSesionesDelDiaMasCercano();

        assertEquals(1, result.size());
        verify(sesionRepository).findProximaSesion(PageRequest.of(0, 1));
        verify(sesionRepository).findSesionesPorFecha(proxima.getFecha().toLocalDate());
    }

    @Test
    void obtenerSesionesDelDiaMasCercanoEmpty() {
        when(sesionRepository.findProximaSesion(PageRequest.of(0, 1))).thenReturn(List.of());

        var result = sesionService.obtenerSesionesDelDiaMasCercano();

        assertTrue(result.isEmpty());
        verify(sesionRepository, never()).findSesionesPorFecha(any());
    }

    @Test
    void obtenerHorasOcupadas() {
        LocalDate fecha = LocalDate.now();
        when(sesionRepository.findHorasOcupadas(fecha, 1L)).thenReturn(List.of(LocalDateTime.now()));

        var result = sesionService.obtenerHorasOcupadas(1L, fecha);

        assertEquals(1, result.size());
        verify(sesionRepository).findHorasOcupadas(fecha, 1L);
    }
}
