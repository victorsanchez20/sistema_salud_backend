package com.salud.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "apellido_paterno")
    private String apaterno;
    @Column(name = "apellido_materno")
    private String amaterno;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "DNI")
    private String dni;
    @Column(name = "historia_clinica")
    private String hc;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "fecha_nacimiento")
    private LocalDate nacimiento;
    @Column(name = "nacionalidad")
    private String nacionalidad;

    

    public Paciente() {
    }

    public Paciente(Long id, String apaterno, String amaterno, String nombre, String dni, String hc, String telefono,
            String direccion, LocalDate nacimiento, String nacionalidad) {
        this.id = id;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.nombre = nombre;
        this.dni = dni;
        this.hc = hc;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nacimiento = nacimiento;
        this.nacionalidad = nacionalidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getHc() {
        return hc;
    }

    public void setHc(String hc) {
        this.hc = hc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    
    

}
