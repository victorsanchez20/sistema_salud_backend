package com.salud.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.salud.demo.models.Cita;
import com.salud.demo.models.CitaRequestDTO;
import com.salud.demo.services.CitaService;

/** Capa HTTP: solo traduce peticiones a llamadas al servicio. */
@RestController
@RequestMapping("api/terapia/cita")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.getAllCitas();
    }

    @DeleteMapping("/{id}")
    public void eliminarCita(@PathVariable Long id) {
        citaService.deletebyid(id);
    }

    @GetMapping("/paciente/{idPaciente}")
    public List<Cita> listarPorPaciente(@PathVariable Long idPaciente) {
        return citaService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/doctor/{idDoctor}")
    public List<Cita> listarPorDoctor(@PathVariable Long idDoctor) {
        return citaService.listarPorDoctor(idDoctor);
    }

    @PostMapping
    public Cita create(@RequestBody CitaRequestDTO dto) {
        return citaService.registrarCita(dto);
    }

    @PutMapping("/{idCita}/diagnostico/{idDiagnostico}")
    public void asignarDiagnostico(@PathVariable Long idCita, @PathVariable Long idDiagnostico) {
        citaService.asignarDiagnostico(idCita, idDiagnostico);
    }
}
