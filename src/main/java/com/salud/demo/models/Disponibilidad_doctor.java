package com.salud.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "disponibilidad_doctor")
public class Disponibilidad_doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor id_doctor;
    @ManyToOne
    @JoinColumn(name = "id_turno", nullable = false)
    private Turno id_turno;
    @Column(name = "fecha")
    private LocalDate fecha;

    public Disponibilidad_doctor(Long id, Doctor id_doctor, Turno id_turno, LocalDate fecha) {
        this.id = id;
        this.id_doctor = id_doctor;
        this.id_turno = id_turno;
        this.fecha = fecha;
    }
    public Disponibilidad_doctor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Doctor getId_doctor() { return id_doctor; }
    public void setId_doctor(Doctor id_doctor) { this.id_doctor = id_doctor; }
    public Turno getId_turno() { return id_turno; }
    public void setId_turno(Turno id_turno) { this.id_turno = id_turno; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
