package com.salud.demo.models;


public class SesionDTO {
    private Long numero_sesion;
    private String fecha;
    private Integer estado;

    public SesionDTO(Long numero_sesion, String fecha, Integer estado) {
        this.numero_sesion = numero_sesion;
        this.fecha = fecha;
        this.estado = estado;
    }
    public SesionDTO() { }

    public Long getNumero_sesion() { return numero_sesion; }
    public void setNumero_sesion(Long numero_sesion) { this.numero_sesion = numero_sesion; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
}
