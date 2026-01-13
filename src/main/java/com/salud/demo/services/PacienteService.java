package com.salud.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente getByHc(String hc) {
        return pacienteRepository.findByHc(hc)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con HC: " + hc));
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

}
