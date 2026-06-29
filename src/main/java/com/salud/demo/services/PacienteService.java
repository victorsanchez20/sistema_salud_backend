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
        Paciente guardado = pacienteRepository.save(paciente);

        // Generar la historia clínica
        guardado.setHc(String.format("HC%06d", guardado.getId()));

        // Guardar nuevamente con la HC
        return pacienteRepository.save(guardado);

    }

    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente getByHc(String hc) {
        return pacienteRepository.findByHc(hc)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con HC: " + hc));
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

    public Paciente login(String dni, String password) {
        Paciente paciente = getByDNI(dni);
        if (paciente == null) {
            return null;
        }

        String apaterno = paciente.getApaterno() != null ? paciente.getApaterno().trim() : "";
        String amaterno = paciente.getAmaterno() != null ? paciente.getAmaterno().trim() : "";
        String hc = paciente.getHc() != null ? paciente.getHc().trim() : "";
        String expectedPassword = apaterno + hc + amaterno;

        if (expectedPassword.equals(password)) {
            return paciente;
        }

        return null;
    }

    public int ultimaHc() {
        Integer maxHc = pacienteRepository.maxHc();
        return maxHc != null ? maxHc : 0;
    }

}
