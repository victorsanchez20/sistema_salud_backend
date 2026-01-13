package com.salud.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.models.Paciente;
import com.salud.demo.services.PacienteService;

@RestController
@RequestMapping("api/terapia/paciente")
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.getAllPacientes();
    }
    @GetMapping("/hc/{hc}")
    public Paciente obtenerPorHC(@PathVariable String hc) {
        return pacienteService.getByHc(hc);
    }
    @PostMapping
    public Paciente create(@RequestBody Paciente paciente) {
        return pacienteService.save(paciente);
    }
    @PutMapping("/{id}")
    public Paciente update(@PathVariable Long id, @RequestBody Paciente paciente) {
        paciente.setId(id);
        return pacienteService.save(paciente);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pacienteService.deleteById(id);
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPaciente(
            @RequestParam String q
    ) {
        return ResponseEntity.ok(
            pacienteService.buscarPaciente(q)
        );
    }
}