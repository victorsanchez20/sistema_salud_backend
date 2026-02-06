package com.salud.demo.models;

import java.time.LocalDate;
import java.util.List;

public class HorarioRequest {

    private Long doctorId;
    private LocalDate fecha;
    private List<String> horas;
    public Long getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public List<String> getHoras() {
        return horas;
    }
    public void setHoras(List<String> horas) {
        this.horas = horas;
    }

    // getters y setters
    
}

