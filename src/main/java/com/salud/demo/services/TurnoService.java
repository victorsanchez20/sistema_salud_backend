package com.salud.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Turno;
import com.salud.demo.repositories.TurnoRepository;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<Turno> getAllTurnos() {
        return turnoRepository.findAll();
    }

    public Turno save(Turno turno) {
        return turnoRepository.save(turno);
    }

    public void deleteById(Long id) {
        turnoRepository.deleteById(id);
    }
}
