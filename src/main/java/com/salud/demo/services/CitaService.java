package com.salud.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Cita;
import com.salud.demo.repositories.CitaRepository;

@Service
public class CitaService {
    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }
    public void deletebyid(Long id) {
        citaRepository.deleteById(id);
    }
    public List<Cita> listarPorPaciente(Long idPaciente) {
        return citaRepository.findByPacienteId(idPaciente);
    }

    
}
