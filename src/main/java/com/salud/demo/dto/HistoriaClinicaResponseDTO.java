package com.salud.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class HistoriaClinicaResponseDTO {
    private Long id;
    private String responsable;
    private String especialidad;
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private List<ArchivoDTO> archivos;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<ArchivoDTO> getArchivos() { return archivos; }
    public void setArchivos(List<ArchivoDTO> archivos) { this.archivos = archivos; }
}
