package com.salud.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.services.Disponibilidad_doctorService;
import com.salud.demo.models.CalendarioGuardadoDTO;
import com.salud.demo.models.DisponibilidadDTO;
import com.salud.demo.models.DisponibilidadViewDTO;
import com.salud.demo.models.Disponibilidad_doctor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/terapia/disponibilidad")
@CrossOrigin(origins = "*")
public class Disponibilidad_doctorController {

    private final Disponibilidad_doctorService disponibilidad_doctorService;

    public Disponibilidad_doctorController(Disponibilidad_doctorService disponibilidad_doctorService) {
        this.disponibilidad_doctorService = disponibilidad_doctorService;
    }

    @GetMapping
    public List<Disponibilidad_doctor> listarDisponibilidad() {
        return disponibilidad_doctorService.getAllDisponibilidad();
    }

    @PostMapping
    public Disponibilidad_doctor create(@RequestBody Disponibilidad_doctor disponibilidad) {
        return disponibilidad_doctorService.save(disponibilidad);
    }

    @PutMapping("/{id}")    
    public Disponibilidad_doctor update(@PathVariable Long id, @RequestBody Disponibilidad_doctor disponibilidad) {
        disponibilidad.setId(id);
        return disponibilidad_doctorService.save(disponibilidad);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        disponibilidad_doctorService.deleteById(id);
    }

    @PostMapping("/cargar")
    public ResponseEntity<?> cargarDisponibilidad(@RequestBody List<DisponibilidadDTO> data) {
        disponibilidad_doctorService.guardarMasivo(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/masivo")
    public ResponseEntity<Void> guardarMasivo(
            @RequestBody List<DisponibilidadDTO> data) {

        disponibilidad_doctorService.guardarMasivo(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public List<DisponibilidadViewDTO> listarPorMesAnio(
            @RequestParam int mes,
            @RequestParam int anio) {
        return disponibilidad_doctorService.listarPorMesAnio(mes, anio);
    }

    @GetMapping("/calendarios")
    public List<CalendarioGuardadoDTO> obtenerCalendarios() {
        return disponibilidad_doctorService.obtenerCalendariosGuardados();
    }

    @DeleteMapping("/calendario")
    public ResponseEntity<Void> eliminarCalendario(
            @RequestParam int mes,
            @RequestParam int anio
    ) {
        disponibilidad_doctorService.eliminarPorMesAnio(mes, anio);
        return ResponseEntity.noContent().build();
    }

}
