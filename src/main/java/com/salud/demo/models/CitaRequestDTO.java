package com.salud.demo.models;

import java.time.LocalDate;
import java.util.List;

public class CitaRequestDTO {

    private Long paciente;
    private Long doctor;
    private Long diagnostico;
    private LocalDate fecha_creacion;
    private Integer numero_cita;
    private List<SesionDTO> sesiones;
    
    public CitaRequestDTO(Long paciente, Long doctor, Long diagnostico, LocalDate fecha_creacion, Integer numero_cita,
            List<SesionDTO> sesiones) {
        this.paciente = paciente;
        this.doctor = doctor;
        this.diagnostico = diagnostico;
        this.fecha_creacion = fecha_creacion;
        this.numero_cita = numero_cita;
        this.sesiones = sesiones;
    }

    public CitaRequestDTO() { }

    public Long getPaciente() { return paciente; }
    public void setPaciente(Long paciente) { this.paciente = paciente; }
    public Long getDoctor() { return doctor; }
    public void setDoctor(Long doctor) { this.doctor = doctor; }
    public Long getDiagnostico() { return diagnostico; }
    public void setDiagnostico(Long diagnostico) { this.diagnostico = diagnostico; }
    public LocalDate getFecha_creacion() { return fecha_creacion; }
    public void setFecha_creacion(LocalDate fecha_creacion) { this.fecha_creacion = fecha_creacion; }
    public Integer getNumero_cita() { return numero_cita; }
    public void setNumero_cita(Integer numero_cita) { this.numero_cita = numero_cita; }
    public List<SesionDTO> getSesiones() { return sesiones; }
    public void setSesiones(List<SesionDTO> sesiones) { this.sesiones = sesiones; }
}
