package com.salud.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Diagnostico;
import com.salud.demo.repositories.DiagnosticoRepository;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
    }

    public List<Diagnostico> getAllDiagnosticos() {
        return diagnosticoRepository.findAll();
    }

    public Diagnostico save(Diagnostico diag) {
        return diagnosticoRepository.save(diag);
    }

    public void deleteById(Long id) {
        diagnosticoRepository.deleteById(id);
    }
}
