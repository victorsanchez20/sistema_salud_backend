package com.salud.demo.models;

import java.time.LocalDate;

public class DisponibilidadViewDTO {

    private LocalDate fecha;
    private String doctor;
    private String turno;

    public DisponibilidadViewDTO(LocalDate fecha, String doctor, String turno) {
        this.fecha = fecha;
        this.doctor = doctor;
        this.turno = turno;
    }

    public LocalDate getFecha() { return fecha; }
    public String getDoctor() { return doctor; }
    public String getTurno() { return turno; }
}
