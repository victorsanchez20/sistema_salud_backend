package com.salud.demo.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.salud.demo.domain.RangoEdad;
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
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (RangoEdad rango : RangoEdad.values()) {
            conteo.put(rango.getEtiqueta(), 0);
        }

        LocalDate hoy = LocalDate.now();
        for (Paciente p : pacienteRepository.findAll()) {
            if (p.getNacimiento() == null) continue;
            int edad = Period.between(p.getNacimiento(), hoy).getYears();
            conteo.merge(RangoEdad.paraEdad(edad).getEtiqueta(), 1, Integer::sum);
        }
        return conteo;
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
