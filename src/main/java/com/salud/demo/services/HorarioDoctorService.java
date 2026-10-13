package com.salud.demo.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salud.demo.exception.RecursoNoEncontradoException;
import com.salud.demo.models.Disponibilidad_doctor;
import com.salud.demo.models.Doctor;
import com.salud.demo.models.HorarioDoctor;
import com.salud.demo.models.Turno;
import com.salud.demo.repositories.Disponibilidad_doctorRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.HorarioDoctorRepository;

import jakarta.transaction.Transactional;

/**
 * Reglas de horarios de los doctores. Antes parte de esta lógica estaba
 * escrita dentro de HorarioDoctorController (que usaba repositorios directos).
 */
@Service
public class HorarioDoctorService {

    private final HorarioDoctorRepository horarioRepo;
    private final DoctorRepository doctorRepo;
    private final Disponibilidad_doctorRepository disponibilidadRepo;

    public HorarioDoctorService(HorarioDoctorRepository horarioRepo,
                                DoctorRepository doctorRepo,
                                Disponibilidad_doctorRepository disponibilidadRepo) {
        this.horarioRepo = horarioRepo;
        this.doctorRepo = doctorRepo;
        this.disponibilidadRepo = disponibilidadRepo;
    }

    public List<HorarioDoctor> listar() {
        return horarioRepo.findAll();
    }

    public List<String> listarHorasBase(Long doctorId) {
        return horarioRepo.findHorasBaseDoctor(doctorId).stream()
            .map(LocalTime::toString)
            .toList();
    }

    @Transactional
    public void guardarHorario(Long doctorId, List<String> horas) {

        Doctor doctor = doctorRepo.findById(doctorId)
            .orElseThrow(() -> RecursoNoEncontradoException.de("Doctor"));

        List<HorarioDoctor> actuales = horarioRepo.findByDoctorId(doctorId);

        Set<LocalTime> horasBD = actuales.stream()
            .map(HorarioDoctor::getHoraInicio)
            .collect(Collectors.toSet());

        Set<LocalTime> horasRequest = horas.stream()
            .map(LocalTime::parse)
            .collect(Collectors.toSet());

        for (LocalTime h : horasBD) {
            if (!horasRequest.contains(h)) {
                horarioRepo.deleteByDoctorIdAndHoraInicio(doctorId, h);
            }
        }

        for (LocalTime h : horasRequest) {
            if (!horasBD.contains(h)) {
                HorarioDoctor nuevo = new HorarioDoctor();
                nuevo.setDoctor(doctor);
                nuevo.setHoraInicio(h);
                horarioRepo.save(nuevo);
            }
        }
    }

    /** Horas libres de un doctor en una fecha: horas del turno menos las ya ocupadas. */
    public List<String> horasDisponibles(Long doctorId, LocalDate fecha) {
        Disponibilidad_doctor disponibilidad = disponibilidadRepo.findByDoctorAndFecha(doctorId, fecha)
            .orElseThrow(() -> new RecursoNoEncontradoException("El doctor no atiende este día"));

        Set<LocalTime> horasOcupadas = horarioRepo.findByDoctorIdAndFecha(doctorId, fecha).stream()
            .map(HorarioDoctor::getHoraInicio)
            .collect(Collectors.toSet());

        return horasDelTurno(disponibilidad.getId_turno()).stream()
            .filter(h -> !horasOcupadas.contains(h))
            .map(LocalTime::toString)
            .toList();
    }

    /** Fechas futuras en las que el doctor todavía tiene al menos una hora libre. */
    public List<LocalDate> fechasDisponibles(Long doctorId) {
        List<LocalDate> fechas = new ArrayList<>();

        for (Disponibilidad_doctor dia : disponibilidadRepo.findDisponibilidadFuturaPorDoctor(doctorId)) {
            int totalHoras = (int) Duration.between(
                dia.getId_turno().getHora_inicio(),
                dia.getId_turno().getHora_fin()
            ).toHours();

            int horasOcupadas = horarioRepo.countByDoctorIdAndFecha(doctorId, dia.getFecha());

            if (horasOcupadas < totalHoras) {
                fechas.add(dia.getFecha());
            }
        }
        return fechas;
    }

    /** Genera las horas de inicio (cada 60 min) comprendidas en un turno. */
    private List<LocalTime> horasDelTurno(Turno turno) {
        List<LocalTime> horas = new ArrayList<>();
        LocalTime hora = turno.getHora_inicio();
        while (hora.isBefore(turno.getHora_fin())) {
            horas.add(hora);
            hora = hora.plusHours(1);
        }
        return horas;
    }
}
