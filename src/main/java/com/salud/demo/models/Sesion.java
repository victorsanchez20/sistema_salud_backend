package com.salud.demo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;
    @Column(name = "numero_sesion")
    private Long numero_sesion;
    @Column(name = "fecha")
    private LocalDateTime fecha;
    @Column(name = "estado")
    private Integer estado;
    
    public Sesion(Long id, Cita cita, Long numero_sesion, LocalDateTime fecha, Integer estado) {
        this.id = id;
        this.cita = cita;
        this.numero_sesion = numero_sesion;
        this.fecha = fecha;
        this.estado = estado;
    }
    public Sesion() {}

    public Long getId() {    return id; }
    public void setId(Long id) { this.id = id; }
    public Cita getId_cita() { return cita; }
    public void setId_cita(Cita cita) { this.cita = cita; }
    public Long getNumero_sesion() { return numero_sesion; }
    public void setNumero_sesion(Long numero_sesion) { this.numero_sesion = numero_sesion; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
}
