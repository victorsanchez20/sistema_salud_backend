package com.salud.demo.models;

import java.util.List;

public class HorarioDTO {

    private Long doctorId;
    private List<String> horas;

    public HorarioDTO() {
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getHoras() {
        return horas;
    }

    public void setHoras(List<String> horas) {
        this.horas = horas;
    }
}
