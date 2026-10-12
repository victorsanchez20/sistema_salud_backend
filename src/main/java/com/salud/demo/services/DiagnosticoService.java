package com.salud.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.services.support.CrudService;

import com.salud.demo.models.Diagnostico;
import com.salud.demo.repositories.DiagnosticoRepository;

@Service
public class DiagnosticoService extends CrudService<Diagnostico, Long> {

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository) {
        super(diagnosticoRepository);
    }

    public List<Diagnostico> getAllDiagnosticos() {
        return findAll();
    }

    public Diagnostico save(Diagnostico diag) {
        return guardar(diag);
    }

    public void deleteById(Long id) {
        eliminarPorId(id);
    }
}
