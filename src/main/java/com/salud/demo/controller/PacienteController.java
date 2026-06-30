package com.salud.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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

import com.salud.demo.dto.LoginRequestDTO;
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
    public ResponseEntity<?> create(@RequestBody Paciente paciente) {

        Paciente existente = pacienteService.getByDNI(paciente.getDni());

        if(existente != null) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "El dni ya existe");

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        Paciente nuevoPaciente = pacienteService.save(paciente);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoPaciente);
        
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

    // OBTIENE DATOS DEL PACIENTE POR BUSQUEDA SIMPLE
    @GetMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPaciente(
            @RequestParam String q
    ) {
        return ResponseEntity.ok(
            pacienteService.buscarPaciente(q)
        );
    }

    // OBTIENE DATOS DEL PACIENTE POR DNI
    @GetMapping("/{dni}")
    public Paciente obtenerPorDNI(@PathVariable String dni) {
        return this.pacienteService.getByDNI(dni);
    }

    // GET COUNT OF PATIENTS
    @GetMapping("/cantidad-pacientes")
    public int cantidadPacientes() {
        return pacienteService.getAllPacientes().size();

    }

    // GET Contar cantidad de pacientes por los 6 ultimos meses contando el mes actual
    @GetMapping("/cantidad-pacientes-ultimos-meses")
    public Map<String, Integer> cantidadPacientesUltimosMeses() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        Map<String, Integer> conteoPorMes = new HashMap<>();

        for (Paciente paciente : pacientes) {
            String mes = paciente.getFechaCreacion().getMonth().toString() + " " + paciente.getFechaCreacion().getYear();
            conteoPorMes.put(mes, conteoPorMes.getOrDefault(mes, 0) + 1);
        }

        return conteoPorMes;
    }

    @GetMapping("/pacientes-por-edad")
    public Map<String, Integer> pacientesPorEdad() {
        return pacienteService.pacientesPorEdad();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPaciente(@RequestBody LoginRequestDTO request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "DNI y contraseña son obligatorios");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Paciente paciente = pacienteService.login(request.getUsername().trim(), request.getPassword());
        if (paciente == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "DNI o contraseña inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(paciente);
    }


}