package com.salud.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salud.demo.services.support.CrudService;

import com.salud.demo.models.Turno;
import com.salud.demo.repositories.TurnoRepository;

@Service
public class TurnoService extends CrudService<Turno, Long> {

    public TurnoService(TurnoRepository turnoRepository) {
        super(turnoRepository);
    }

    public List<Turno> getAllTurnos() {
        return findAll();
    }

    public Turno save(Turno turno) {
        return guardar(turno);
    }

    public void deleteById(Long id) {
        eliminarPorId(id);
    }
}
