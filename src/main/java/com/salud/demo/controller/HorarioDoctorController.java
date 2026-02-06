package com.salud.demo.controller;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.salud.demo.models.*;
import com.salud.demo.repositories.*;
import com.salud.demo.services.HorarioDoctorService;

@RestController
@RequestMapping("/horarios")
@CrossOrigin(origins = "*")
public class HorarioDoctorController {

    @Autowired
    private HorarioDoctorRepository horarioRepo;

    @Autowired private HorarioDoctorService horarioService;

    @Autowired
    private Disponibilidad_doctorRepository disponibilidadRepo;

    @GetMapping
    public List<HorarioDoctor> listar() {
        return this.horarioRepo.findAll();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<String> listarHorasPorDoctor(@PathVariable Long doctorId) {
        return horarioRepo.findHorasBaseDoctor(doctorId)
                .stream()
                .map(LocalTime::toString)
                .toList();
    }


    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody HorarioDTO dto) {
        horarioService.guardarHorario(dto.getDoctorId(), dto.getHoras());
        return ResponseEntity.ok().build();
    }



    /* =========================
       HORAS DISPONIBLES
       ========================= */
    @GetMapping("/horas-disponibles")
    public ResponseEntity<List<String>> horasDisponibles(
            @RequestParam Long doctorId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha
    ) {

        // 1️⃣ disponibilidad del doctor ese día
        Disponibilidad_doctor disp =
            disponibilidadRepo.findByDoctorAndFecha(doctorId, fecha)
            .orElseThrow(() ->
                new RuntimeException("El doctor no atiende este día")
            );

        Turno turno = disp.getId_turno();

        // 2️⃣ generar horas base desde el turno
        List<LocalTime> horasBase = new ArrayList<>();
        LocalTime t = turno.getHora_inicio();
        while (t.isBefore(turno.getHora_fin())) {
            horasBase.add(t);
            t = t.plusHours(1);
        }

        // 3️⃣ horas ocupadas
        Set<LocalTime> horasOcupadas =
            horarioRepo.findByDoctorIdAndFecha(doctorId, fecha)
                .stream()
                .map(HorarioDoctor::getHoraInicio)
                .collect(Collectors.toSet());

        // 4️⃣ filtrar horas disponibles
        List<String> disponibles = horasBase.stream()
            .filter(h -> !horasOcupadas.contains(h))
            .map(LocalTime::toString)
            .toList();

        return ResponseEntity.ok(disponibles);
    }

    /* =========================
       FECHAS DISPONIBLES
       ========================= */
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<LocalDate>> fechasDisponibles(
            @RequestParam Long doctorId
    ) {

        List<Disponibilidad_doctor> dias =
            disponibilidadRepo.findDisponibilidadFuturaPorDoctor(doctorId);

        List<LocalDate> fechasDisponibles = new ArrayList<>();

        for (Disponibilidad_doctor d : dias) {

            Turno turno = d.getId_turno();

            int totalHoras = (int) Duration.between(
                    turno.getHora_inicio(),
                    turno.getHora_fin()
            ).toHours();

            int horasOcupadas =
                horarioRepo.countByDoctorIdAndFecha(
                    doctorId,
                    d.getFecha()
                );

            // ✅ si queda al menos una hora libre
            if (horasOcupadas < totalHoras) {
                fechasDisponibles.add(d.getFecha());
            }
        }

        return ResponseEntity.ok(fechasDisponibles);
    }
}
