package com.salud.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "id_diagnostico", nullable = false)
    private Diagnostico diagnostico;
    @Column(name = "fecha_creacion_cita")
    private LocalDate fecha_creacion;
    @Column(name = "numero_cita")
    private Integer numero_cita;

    public Cita(Long id, Paciente paciente, Doctor doctor, Diagnostico diagnostico, LocalDate fecha_creacion,
            Integer numero_cita) {
        this.id = id;
        this.paciente = paciente;
        this.doctor = doctor;
        this.diagnostico = diagnostico;
        this.fecha_creacion = fecha_creacion;
        this.numero_cita = numero_cita;
    }
    public Cita() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getId_paciente() { return paciente; }
    public void setId_paciente(Paciente paciente) { this.paciente = paciente; }
    public Doctor getId_doctor() { return doctor; }
    public void setId_doctor(Doctor id_doctor) { this.doctor = id_doctor; }
    public Diagnostico getId_diagnostico() { return diagnostico; }
    public void setId_diagnostico(Diagnostico id_diagnostico) { this.diagnostico = id_diagnostico; }
    public LocalDate getFecha_creacion() { return fecha_creacion; }
    public void setFecha_creacion(LocalDate fecha_creacion) { this.fecha_creacion = fecha_creacion; }
    public Integer getNumero_cita() { return numero_cita; }
    public void setNumero_cita(Integer numero_cita) { this.numero_cita = numero_cita; }
}
