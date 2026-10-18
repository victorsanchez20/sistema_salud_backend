package com.salud.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.dto.HistoriaClinicaDTO;
import com.salud.demo.dto.HistoriaClinicaResponseDTO;
import com.salud.demo.services.HistoriaClinicaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/historia-clinica")
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> guardar(
        @RequestBody HistoriaClinicaDTO dto) {
        
            service.guardar(dto);

            return ResponseEntity.ok(
                Map.of("mensaje", "Historia clínica guardada correctamente.")
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> actualizar(
        @PathVariable Long id, 
        @RequestBody HistoriaClinicaDTO dto) {
        
        service.actualizar(id, dto);

        return ResponseEntity.ok(
            Map.of("mensaje", "Historia clínica actualizada correctamente.")
        );
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}
