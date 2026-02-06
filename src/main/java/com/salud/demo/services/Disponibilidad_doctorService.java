package com.salud.demo.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salud.demo.models.CalendarioGuardadoDTO;
import com.salud.demo.models.DisponibilidadDTO;
import com.salud.demo.models.DisponibilidadViewDTO;
import com.salud.demo.models.Disponibilidad_doctor;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.Turno;
import com.salud.demo.repositories.Disponibilidad_doctorRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.TurnoRepository;

import jakarta.transaction.Transactional;

@Service
public class Disponibilidad_doctorService {

    private final Disponibilidad_doctorRepository disponibilidad_doctorRepository;
    private final DoctorRepository doctorRepository;
    private final TurnoRepository turnoRepository;

    @Autowired
    private Disponibilidad_doctorRepository disponibilidadRepo;

    public Disponibilidad_doctorService(
        Disponibilidad_doctorRepository disponibilidad_doctorRepository,
        DoctorRepository doctorRepository,
        TurnoRepository turnoRepository) {
        this.disponibilidad_doctorRepository = disponibilidad_doctorRepository;
        this.doctorRepository = doctorRepository;
        this.turnoRepository = turnoRepository;
    }

    public List<Disponibilidad_doctor> getAllDisponibilidad() {
        return disponibilidad_doctorRepository.findAll();
    }

    public Disponibilidad_doctor save(Disponibilidad_doctor dis) {
        return disponibilidad_doctorRepository.save(dis);
    }

    public void deleteById(Long id) {
        disponibilidad_doctorRepository.deleteById(id);
    }

    public void guardarMasivo(List<DisponibilidadDTO> data) {
        for (DisponibilidadDTO dto : data) {

            Doctor doctor = doctorRepository.findById(dto.getIdDoctor())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

            Turno turno = turnoRepository.findById(dto.getIdTurno())
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

            Disponibilidad_doctor d = new Disponibilidad_doctor();  
            d.setId_doctor(doctor);
            d.setId_turno(turno);
            d.setFecha(dto.getFecha());

            disponibilidad_doctorRepository.save(d);
        }
    }

    public List<DisponibilidadViewDTO> listarPorMesAnio(int mes, int anio) {

        return disponibilidad_doctorRepository
            .findByMesAndAnio(mes, anio)
            .stream()
            .map(d -> new DisponibilidadViewDTO(
                d.getFecha(),
                d.getId_doctor().getNombre(),
                d.getId_turno().getCodigo()
            ))
            .toList();
    }

    public List<CalendarioGuardadoDTO> obtenerCalendariosGuardados() {
        return disponibilidad_doctorRepository.obtenerCalendariosGuardados();
    }
    
    @Transactional
    public void eliminarPorMesAnio(int mes, int anio) {
        disponibilidad_doctorRepository.deleteByMesAndAnio(mes, anio);
    }

    public List<Disponibilidad_doctor> listarPorDoctor(Long idDoctor) {
        // Cambia findByDoctor_Id por el nuevo nombre del método
        return disponibilidad_doctorRepository.buscarPorDoctorId(idDoctor);
    }

    /**
     * Devuelve las fechas disponibles de un doctor
     * según una hora específica
     */
    public List<LocalDate> fechasDisponibles(
            Long doctorId,
            LocalTime hora
    ) {

        List<Disponibilidad_doctor> lista =
                disponibilidadRepo.findDisponibilidadFuturaPorDoctor(doctorId);

        return lista.stream()
                .filter(d ->
                        !hora.isBefore(d.getId_turno().getHora_inicio()) &&
                        hora.isBefore(d.getId_turno().getHora_fin())
                )
                .map(Disponibilidad_doctor::getFecha)
                .distinct()
                .sorted()
                .toList();
    }
}

