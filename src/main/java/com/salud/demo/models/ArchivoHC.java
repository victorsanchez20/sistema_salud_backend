package com.salud.demo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "archivo_hc")
public class ArchivoHC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @Column(name = "tipo_archivo")
    private String tipoArchivo;

    @Column(name = "nombre_original")
    private String nombreOriginal;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "url")
    private String url;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void PrePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public ArchivoHC(Long id, HistoriaClinica historiaClinica, String tipoArchivo, String nombreOriginal,
            String categoria, String url, LocalDateTime createdAt) {
        this.id = id;
        this.historiaClinica = historiaClinica;
        this.tipoArchivo = tipoArchivo;
        this.nombreOriginal = nombreOriginal;
        this.categoria = categoria;
        this.url = url;
        this.createdAt = createdAt;
    }

    public ArchivoHC() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String tipoArchivo) { this.tipoArchivo = tipoArchivo; }
    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }  
}
