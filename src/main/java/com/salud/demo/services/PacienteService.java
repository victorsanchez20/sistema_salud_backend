package com.salud.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.exception.RecursoNoEncontradoException;
import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.PacienteRepository;
import com.salud.demo.services.support.HistoriaClinicaCodeGenerator;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final HistoriaClinicaCodeGenerator historiaClinicaCodeGenerator;

    public PacienteService(PacienteRepository pacienteRepository, 
                            HistoriaClinicaCodeGenerator historiaClinicaCodeGenerator) {
        this.pacienteRepository = pacienteRepository;
        this.historiaClinicaCodeGenerator = historiaClinicaCodeGenerator;
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente save(Paciente paciente) {

        Paciente guardado = pacienteRepository.save(paciente);
        // Generar la historia clínica
        guardado.setHc(historiaClinicaCodeGenerator.generar(guardado.getId()));
        // Guardar nuevamente con la HC
        return pacienteRepository.save(guardado);

    }

    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente getByHc(String hc) {
        return pacienteRepository.findByHc(hc)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con HC: " + hc));
    }

    public Paciente getByDNI(String dni) {
        return pacienteRepository.findByDni(dni)
            .orElse(null);
    }

    public List<Paciente> buscarPaciente(String texto) {
        String[] palabras = texto.split(" ");
        List<Paciente> resultados = new ArrayList<>();

        for (String palabra: palabras) {
            resultados.addAll(
                pacienteRepository.buscarPorNombreCompleto(palabra)
            );
        }

        return resultados.stream().distinct().toList();
    }

    public int ultimaHc() {
        Integer maxHc = pacienteRepository.maxHc();
        return maxHc != null ? maxHc : 0;
    }

}
