package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Paciente;

@ExtendWith(MockitoExtension.class)
class PacienteAuthServiceTest {

    @Mock private PacienteService pacienteService;

    @InjectMocks
    private PacienteAuthService authService;

    private Paciente paciente() {
        Paciente p = new Paciente();
        p.setApaterno("Garcia");
        p.setAmaterno("Lopez");
        p.setHc("HC000001");
        return p;
    }

    @Test
    void loginCorrectoDevuelveElPaciente() {
        Paciente p = paciente();
        when(pacienteService.getByDNI("12345678")).thenReturn(p);

        assertSame(p, authService.login("12345678", "GarciaHC000001Lopez"));
    }

    @Test
    void loginConClaveIncorrectaDevuelveNull() {
        when(pacienteService.getByDNI("12345678")).thenReturn(paciente());

        assertNull(authService.login("12345678", "otra"));
    }

    @Test
    void loginConDniInexistenteDevuelveNull() {
        when(pacienteService.getByDNI("00000000")).thenReturn(null);

        assertNull(authService.login("00000000", "x"));
    }
}
