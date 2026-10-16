package com.salud.demo.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EstadoSesionTest {

    @Test
    void conservaLosCodigosHistoricosDeLaBaseDeDatos() {
        assertEquals(1, EstadoSesion.REGISTRADA.getCodigo());
        assertEquals(2, EstadoSesion.COMPLETADA.getCodigo());
        assertEquals(3, EstadoSesion.CANCELADA.getCodigo());
    }

    @Test
    void conservaLasClavesDelIndicadorJson() {
        assertEquals("registrada", EstadoSesion.REGISTRADA.getClave());
        assertEquals("completada", EstadoSesion.COMPLETADA.getClave());
        assertEquals("cancelada", EstadoSesion.CANCELADA.getClave());
    }
}
