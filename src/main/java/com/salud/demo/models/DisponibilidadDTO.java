package com.salud.demo.models;

import java.time.LocalDate;

public class DisponibilidadDTO {
    private Long idDoctor;
    private Long idTurno;
    private LocalDate fecha;

    public DisponibilidadDTO() {}
    public DisponibilidadDTO(Long idDoctor, Long idTurno, LocalDate fecha) {
        this.idDoctor = idDoctor;
        this.idTurno = idTurno;
        this.fecha = fecha;
    }

    public Long getIdDoctor() { return idDoctor; }
    public void setIdDoctor(Long idDoctor) { this.idDoctor = idDoctor; }
    public Long getIdTurno() { return idTurno; }
    public void setIdTurno(Long idTurno) { this.idTurno = idTurno; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}