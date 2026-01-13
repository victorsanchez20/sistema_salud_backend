package com.salud.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

import com.salud.demo.models.Sesion;
import com.salud.demo.repositories.SesionRepository;
import com.salud.demo.services.SesionService;

@RestController
@RequestMapping("api/terapia/sesion")
@CrossOrigin(origins = "*")
public class SesionController {
    private final SesionService sesionService;
    private final SesionRepository sesionRepository;

    public SesionController(SesionService sesionService, SesionRepository sesionRepository) {
        this.sesionService = sesionService;
        this.sesionRepository = sesionRepository;
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
        return sesionRepository.findHorasOcupadas(fecha, doctorId);
    }

     @GetMapping("/proximas")
    public List<Sesion> sesionesDiaMasCercano() {
        return sesionService.obtenerSesionesDelDiaMasCercano();
    }


}
