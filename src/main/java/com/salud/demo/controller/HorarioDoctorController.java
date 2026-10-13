package com.salud.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.salud.demo.models.HorarioDTO;
import com.salud.demo.models.HorarioDoctor;
import com.salud.demo.services.HorarioDoctorService;

@RestController
@RequestMapping("/horarios")
public class HorarioDoctorController {

    private final HorarioDoctorService horarioService;

    public HorarioDoctorController(HorarioDoctorService horarioService) {
        this.horarioService = horarioService;
    }

    @GetMapping
    public List<HorarioDoctor> listar() {
        return horarioService.listar();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<String> listarHorasPorDoctor(@PathVariable Long doctorId) {
        return horarioService.listarHorasBase(doctorId);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Void> guardar(@RequestBody HorarioDTO dto) {
        horarioService.guardarHorario(dto.getDoctorId(), dto.getHoras());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/horas-disponibles")
    public ResponseEntity<List<String>> horasDisponibles(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(horarioService.horasDisponibles(doctorId, fecha));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<LocalDate>> fechasDisponibles(@RequestParam Long doctorId) {
        return ResponseEntity.ok(horarioService.fechasDisponibles(doctorId));
    }
}
