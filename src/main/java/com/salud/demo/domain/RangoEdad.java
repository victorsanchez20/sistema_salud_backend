package com.salud.demo.domain;

/**
 * Rangos etarios del indicador "pacientes por edad".
 * Los rangos se declaran en orden ascendente; para cambiar o agregar uno
 * solo se edita este enum, no la lógica de conteo (OCP).
 */
public enum RangoEdad {
    DE_0_A_10("0-10", 10),
    DE_11_A_20("11-20", 20),
    DE_21_A_30("21-30", 30),
    DE_31_A_40("31-40", 40),
    DE_41_A_50("41-50", 50),
    MAS_DE_50("50+", Integer.MAX_VALUE);

    private final String etiqueta;
    private final int edadMaxima;

    RangoEdad(String etiqueta, int edadMaxima) {
        this.etiqueta = etiqueta;
        this.edadMaxima = edadMaxima;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /** Devuelve el primer rango cuyo límite superior alcanza la edad dada. */
    public static RangoEdad paraEdad(int edad) {
        for (RangoEdad rango : values()) {
            if (edad <= rango.edadMaxima) {
                return rango;
            }
        }
        return MAS_DE_50;
    }
}
