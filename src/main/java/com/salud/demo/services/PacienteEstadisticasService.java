package com.salud.demo.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.PacienteRepository;

/** Indicadores del panel administrativo sobre pacientes (antes dentro de PacienteService/Controller). */
@Service
public class PacienteEstadisticasService {

    private final PacienteRepository pacienteRepository;

    public PacienteEstadisticasService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Map<String, Integer> pacientesPorEdad() {
        Map<String, Integer> rangos = new LinkedHashMap<>();
        rangos.put("0-10", 0);
        rangos.put("11-20", 0);
        rangos.put("21-30", 0);
        rangos.put("31-40", 0);
        rangos.put("41-50", 0);
        rangos.put("50+", 0);

        LocalDate hoy = LocalDate.now();
        for (Paciente p : pacienteRepository.findAll()) {
            if (p.getNacimiento() == null) continue;
            int edad = Period.between(p.getNacimiento(), hoy).getYears();
            if (edad <= 10) rangos.put("0-10", rangos.get("0-10") + 1);
            else if (edad <= 20) rangos.put("11-20", rangos.get("11-20") + 1);
            else if (edad <= 30) rangos.put("21-30", rangos.get("21-30") + 1);
            else if (edad <= 40) rangos.put("31-40", rangos.get("31-40") + 1);
            else if (edad <= 50) rangos.put("41-50", rangos.get("41-50") + 1);
            else rangos.put("50+", rangos.get("50+") + 1);
        }
        return rangos;
    }

    public Map<String, Integer> pacientesPorMes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        Map<String, Integer> conteoPorMes = new LinkedHashMap<>();

        for (Paciente paciente : pacientes) {
            String mes = paciente.getFechaCreacion().getMonth().toString() + " " + paciente.getFechaCreacion().getYear();
            conteoPorMes.merge(mes, 1, Integer::sum);
        }
        return conteoPorMes;
    }
}
