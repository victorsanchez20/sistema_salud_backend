package com.salud.demo.models;

public class CalendarioGuardadoDTO {

    private int mes;
    private int anio;
    private long totalTurnos;

    public CalendarioGuardadoDTO(int mes, int anio, long totalTurnos) {
        this.mes = mes;
        this.anio = anio;
        this.totalTurnos = totalTurnos;
    }

    public int getMes() { return mes; }
    public int getAnio() { return anio; }
    public long getTotalTurnos() { return totalTurnos; }
}
