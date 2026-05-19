package com.salud.demo.controller;

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

    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service) {
        this.service = service;
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
    
    
}
