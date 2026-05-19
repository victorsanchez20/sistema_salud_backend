package com.salud.demo.dto;

import java.util.List;

public class HistoriaClinicaDTO {
    private Long pacienteId;
    private String responsable;
    private String especialidad;
    private String descripcion;
    private List<ArchivoDTO> archivos;

    // Getters y setters
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public List<ArchivoDTO> getArchivos() { return archivos; }
    public void setArchivos(List<ArchivoDTO> archivos) { this.archivos = archivos; }
}
