package com.salud.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.salud.demo.models.*;
import com.salud.demo.repositories.*;
import com.salud.demo.services.CitaService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/terapia/cita")
@CrossOrigin(origins = "*")
public class CitaController {

    private final SesionRepository sesionRepository;
    
    @Autowired
    private CitaRepository citaRepository;

    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final CitaService citaService;

    public CitaController(CitaService citaService, PacienteRepository pacienteRepository, DoctorRepository doctorRepository, DiagnosticoRepository diagnosticoRepository, SesionRepository sesionRepository) {
        this.citaService = citaService;
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.diagnosticoRepository = diagnosticoRepository;
        this.sesionRepository = sesionRepository; 
    }
    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.getAllCitas();
    }
    /*
    @PostMapping
    public Cita create(@RequestBody Cita cita) {
        return citaService.save(cita);
    }*/
    @DeleteMapping("/{id}")
    public void eliminarCita(@PathVariable Long id) {
        citaService.deletebyid(id);
    }
    @GetMapping("/paciente/{idPaciente}")
    public List<Cita> listarPorPaciente(@PathVariable Long idPaciente) {
        return citaService.listarPorPaciente(idPaciente);
    }

    @PostMapping
    @Transactional
    public Cita create(@RequestBody CitaRequestDTO dto) {

        Paciente paciente = pacienteRepository
                            .findById(dto.getPaciente())
                            .orElseThrow();
        Doctor doctor = doctorRepository
                            .findById(dto.getDoctor())
                            .orElseThrow();

        Cita cita = new Cita();
        cita.setId_paciente(paciente);
        cita.setId_doctor(doctor);
        cita.setFecha_creacion(dto.getFecha_creacion());
        cita.setNumero_cita(dto.getNumero_cita());

        Cita citaGuardada = citaService.save(cita);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (SesionDTO s : dto.getSesiones()) {
            Sesion sesion = new Sesion();
            sesion.setId_cita(citaGuardada);
            sesion.setNumero_sesion(s.getNumero_sesion());

            // ✅ Calcula la fecha aquí, por cada sesión
            LocalDateTime fechaSesion = LocalDateTime.parse(s.getFecha(), formatter);
            sesion.setFecha(fechaSesion);

            sesion.setEstado(s.getEstado());
            sesionRepository.save(sesion);
        }

        return citaGuardada;
    }

    @PutMapping("/{idCita}/diagnostico/{idDiagnostico}")
    public void asignarDiagnostico(
            @PathVariable Long idCita,
            @PathVariable Long idDiagnostico
    ) {
        Cita cita = citaRepository.findById(idCita).orElseThrow();
        Diagnostico diag = diagnosticoRepository.findById(idDiagnostico).orElseThrow();
        cita.setId_diagnostico(diag);
        citaRepository.save(cita);
    }


    @GetMapping("/doctor/{idDoctor}")
    public List<Cita> listarPorDoctor(@PathVariable Long idDoctor) {
        return citaRepository.findByDoctorId(idDoctor);
    }


}
