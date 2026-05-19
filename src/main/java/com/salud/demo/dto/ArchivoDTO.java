package com.salud.demo.dto;

public class ArchivoDTO {
    private String url;
    private String tipoArchivo;
    private String nombreOriginal;
    private String categoria;

    // Getters y setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String tipoArchivo) { this.tipoArchivo = tipoArchivo; }
    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
