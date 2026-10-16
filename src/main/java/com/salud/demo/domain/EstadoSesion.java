package com.salud.demo.domain;

/**
 * Estados posibles de una sesión de terapia.
 * Reemplaza los "números mágicos" 1/2/3 repartidos por el código.
 * Para agregar un estado nuevo basta con añadir una constante aquí:
 * los reportes que recorren values() lo incluyen sin modificarse (OCP).
 */
public enum EstadoSesion {
    REGISTRADA(1, "registrada"),
    COMPLETADA(2, "completada"),
    CANCELADA(3, "cancelada");

    private final int codigo;
    private final String clave;

    EstadoSesion(int codigo, String clave) {
        this.codigo = codigo;
        this.clave = clave;
    }

    public int getCodigo() {
        return codigo;
    }

    /** Clave usada en la respuesta JSON del indicador (compatible con el frontend). */
    public String getClave() {
        return clave;
    }
}
