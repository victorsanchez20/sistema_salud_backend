package com.salud.demo.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangoEdadTest {

    @Test
    void clasificaLosLimitesDeCadaRango() {
        assertEquals(RangoEdad.DE_0_A_10, RangoEdad.paraEdad(0));
        assertEquals(RangoEdad.DE_0_A_10, RangoEdad.paraEdad(10));
        assertEquals(RangoEdad.DE_11_A_20, RangoEdad.paraEdad(11));
        assertEquals(RangoEdad.DE_21_A_30, RangoEdad.paraEdad(30));
        assertEquals(RangoEdad.DE_31_A_40, RangoEdad.paraEdad(31));
        assertEquals(RangoEdad.DE_41_A_50, RangoEdad.paraEdad(50));
        assertEquals(RangoEdad.MAS_DE_50, RangoEdad.paraEdad(51));
        assertEquals(RangoEdad.MAS_DE_50, RangoEdad.paraEdad(110));
    }

    @Test
    void lasEtiquetasCoincidenConLasClavesQueUsaElFrontend() {
        assertEquals("0-10", RangoEdad.DE_0_A_10.getEtiqueta());
        assertEquals("50+", RangoEdad.MAS_DE_50.getEtiqueta());
    }
}
