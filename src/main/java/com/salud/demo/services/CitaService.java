package com.salud.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.exception.RecursoNoEncontradoException;
import com.salud.demo.models.Cita;
import com.salud.demo.models.CitaRequestDTO;
import com.salud.demo.models.Diagnostico;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.Paciente;
import com.salud.demo.models.Sesion;
import com.salud.demo.models.SesionDTO;
import com.salud.demo.repositories.CitaRepository;
import com.salud.demo.repositories.DiagnosticoRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.PacienteRepository;
import com.salud.demo.repositories.SesionRepository;
import com.salud.demo.services.support.CrudService;

import jakarta.transaction.Transactional;

/**
 * Casos de uso de citas. La orquestación del registro de una cita con sus
 * sesiones vive aquí (antes estaba en CitaController, que además dependía de
 * cinco repositorios).
 */
@Service
public class CitaService extends CrudService<Cita, Long> {

    private static final DateTimeFormatter FORMATO_SESION = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final SesionRepository sesionRepository;

    public CitaService(CitaRepository citaRepository,
                       PacienteRepository pacienteRepository,
                       DoctorRepository doctorRepository,
                       DiagnosticoRepository diagnosticoRepository,
                       SesionRepository sesionRepository) {
        super(citaRepository);
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.diagnosticoRepository = diagnosticoRepository;
        this.sesionRepository = sesionRepository;
    }

    public List<Cita> getAllCitas() {
        return findAll();
    }

    public Cita save(Cita cita) {
        return guardar(cita);
    }

    public void deletebyid(Long id) {
        eliminarPorId(id);
    }

    public List<Cita> listarPorPaciente(Long idPaciente) {
        return citaRepository.findByPacienteId(idPaciente);
    }

    public List<Cita> listarPorDoctor(Long idDoctor) {
        return citaRepository.findByDoctorId(idDoctor);
    }

    /** Registra la cita y todas sus sesiones en una sola transacción. */
    @Transactional
    public Cita registrarCita(CitaRequestDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPaciente())
            .orElseThrow(() -> RecursoNoEncontradoException.de("Paciente"));
        Doctor doctor = doctorRepository.findById(dto.getDoctor())
            .orElseThrow(() -> RecursoNoEncontradoException.de("Doctor"));

        Cita cita = new Cita();
        cita.setId_paciente(paciente);
        cita.setId_doctor(doctor);
        cita.setFecha_creacion(dto.getFecha_creacion());
        cita.setNumero_cita(dto.getNumero_cita());

        Cita citaGuardada = save(cita);
        registrarSesiones(citaGuardada, dto.getSesiones());
        return citaGuardada;
    }

    public void asignarDiagnostico(Long idCita, Long idDiagnostico) {
        Cita cita = citaRepository.findById(idCita)
            .orElseThrow(() -> RecursoNoEncontradoException.de("Cita"));
        Diagnostico diagnostico = diagnosticoRepository.findById(idDiagnostico)
            .orElseThrow(() -> RecursoNoEncontradoException.de("Diagnóstico"));
        cita.setId_diagnostico(diagnostico);
        citaRepository.save(cita);
    }

    private void registrarSesiones(Cita cita, List<SesionDTO> sesiones) {
        for (SesionDTO s : sesiones) {
            Sesion sesion = new Sesion();
            sesion.setId_cita(cita);
            sesion.setNumero_sesion(s.getNumero_sesion());
            sesion.setFecha(LocalDateTime.parse(s.getFecha(), FORMATO_SESION));
            sesion.setEstado(s.getEstado());
            sesionRepository.save(sesion);
        }
    }
}
