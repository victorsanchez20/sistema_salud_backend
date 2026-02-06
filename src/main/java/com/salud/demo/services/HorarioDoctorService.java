package com.salud.demo.services;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.salud.demo.models.Doctor;
import com.salud.demo.models.HorarioDoctor;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.HorarioDoctorRepository;

import jakarta.transaction.Transactional;

@Service
public class HorarioDoctorService {

    @Autowired
    private HorarioDoctorRepository horarioRepo;

    //@Autowired
    //private Disponibilidad_doctorRepository disponibilidadRepo;

    @Autowired private DoctorRepository doctorRepo;

    //@Autowired
    //private SesionRepository sesionRepo;

    @Transactional
    public void guardarHorario(Long doctorId, List<String> horas) {

        Doctor doctor = doctorRepo.findById(doctorId)
            .orElseThrow();

        // 🔹 horas actuales en BD
        List<HorarioDoctor> actuales = horarioRepo.findByDoctorId(doctorId);

        Set<LocalTime> horasBD = actuales.stream()
            .map(HorarioDoctor::getHoraInicio)
            .collect(Collectors.toSet());

        Set<LocalTime> horasRequest = horas.stream()
            .map(LocalTime::parse)
            .collect(Collectors.toSet());

        // ❌ ELIMINAR horas quitadas
        for (LocalTime h : horasBD) {
            if (!horasRequest.contains(h)) {
                horarioRepo.deleteByDoctorIdAndHoraInicio(doctorId, h);
            }
        }

        // ➕ AGREGAR horas nuevas
        for (LocalTime h : horasRequest) {
            if (!horasBD.contains(h)) {
                HorarioDoctor nuevo = new HorarioDoctor();
                nuevo.setDoctor(doctor);
                nuevo.setHoraInicio(h);
                horarioRepo.save(nuevo);
            }
        }
    }

}
