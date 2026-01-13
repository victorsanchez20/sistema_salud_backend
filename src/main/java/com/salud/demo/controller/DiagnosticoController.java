package com.salud.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.models.Diagnostico;
import com.salud.demo.services.DiagnosticoService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/terapia/diagnostico")
@CrossOrigin(origins = "*")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @GetMapping
    public List<Diagnostico> listarDiagnostico() {
        return diagnosticoService.getAllDiagnosticos();
    }

    @PostMapping
    public Diagnostico create( @RequestBody Diagnostico diagnostico) {
        return diagnosticoService.save(diagnostico);
    }

    @PutMapping("/{id}")
    public Diagnostico update(@PathVariable Long id, @RequestBody Diagnostico diagnostico) {
        diagnostico.setId(id);
        return diagnosticoService.save(diagnostico);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        diagnosticoService.deleteById(id);
    }
    
    
}
