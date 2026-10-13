package com.salud.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.salud.demo.models.Sesion;
import com.salud.demo.services.SesionService;

@RestController
@RequestMapping("api/terapia/sesion")
public class SesionController {
    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }
    @GetMapping
    public List<Sesion> listarSesiones() {
        return sesionService.getAllSesions();
    }
    @PostMapping
    public Sesion create(@RequestBody Sesion sesion) {
        return sesionService.save(sesion);
    }
    @PutMapping("/{id}")
    public Sesion update(@PathVariable Long id, @RequestBody Sesion sesion) {
        sesion.setId(id);
        return sesionService.save(sesion);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sesionService.deleteById(id);
    }

    @GetMapping("/paciente/{idPaciente}")
    public List<Sesion> listarSesionesPorPaciente(@PathVariable Long idPaciente) {
        return sesionService.getSesionesPorPaciente(idPaciente);
    }

    @GetMapping("/cita/{idCita}")
    public List<Sesion> listarSesionesPorCita(@PathVariable Long idCita) {
        return sesionService.getSesionesPorCita(idCita);
    }


    @GetMapping("/sesiones-ocupadas")
    public List<LocalDateTime> horasOcupadas(
            @RequestParam LocalDate fecha,
            @RequestParam Long doctorId
    ) {
        return sesionService.obtenerHorasOcupadas(doctorId, fecha);
    }

     @GetMapping("/proximas")
    public List<Sesion> sesionesDiaMasCercano() {
        return sesionService.obtenerSesionesDelDiaMasCercano();
    }

    @PutMapping("/{id}/estado")
    public Sesion actualizarEstado(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        return sesionService.actualizarEstado(id, body.get("estado"));
    }

    @GetMapping("/estados-count")
    public Map<String, Long> contarEstados() {
        return sesionService.contarPorEstado();
    }
}