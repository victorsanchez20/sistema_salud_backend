package com.salud.demo.controller;

import com.salud.demo.repositories.HistoriaClinicaRepository;
import com.salud.demo.services.PacienteService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.dto.HistoriaClinicaDTO;
import com.salud.demo.dto.HistoriaClinicaResponseDTO;
import com.salud.demo.services.HistoriaClinicaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/historia-clinica")
@CrossOrigin(origins = "*")
public class HistoriaClinicaController {

    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final PacienteService pacienteService;
    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service, PacienteService pacienteService, HistoriaClinicaRepository historiaClinicaRepository) {
        this.service = service;
        this.pacienteService = pacienteService;
        this.historiaClinicaRepository = historiaClinicaRepository;
    }

    // POST /api/historia-clinica -> guardar HC + archivos
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody HistoriaClinicaDTO dto) {
        try {
            service.guardar(dto);
            return ResponseEntity.ok("Historia clinica guardada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // GET /api/historia-clinica/paciente/{id}
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
    
    @GetMapping("/ultima-hc")
    public Integer obtenerUltimaHc() {
        return pacienteService.ultimaHc();
    }
    
}
