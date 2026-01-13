package com.salud.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.models.Cita;
import com.salud.demo.models.CitaRequestDTO;
import com.salud.demo.models.Diagnostico;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.Paciente;
import com.salud.demo.models.Sesion;
import com.salud.demo.models.SesionDTO;
import com.salud.demo.repositories.DiagnosticoRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.PacienteRepository;
import com.salud.demo.repositories.SesionRepository;
import com.salud.demo.services.CitaService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/terapia/cita")
@CrossOrigin(origins = "*")
public class CitaController {

    private final SesionRepository sesionRepository;

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

        Paciente paciente = pacienteRepository.findById(dto.getPaciente()).orElseThrow();
        Doctor doctor = doctorRepository.findById(dto.getDoctor()).orElseThrow();
        Diagnostico diagnostico = diagnosticoRepository.findById(dto.getDiagnostico()).orElseThrow();

        Cita cita = new Cita();
        cita.setId_paciente(paciente);
        cita.setId_doctor(doctor);
        cita.setId_diagnostico(diagnostico);
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


}
