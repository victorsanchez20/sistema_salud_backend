package com.salud.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salud.demo.models.Turno;
import com.salud.demo.services.TurnoService;

@RestController
@RequestMapping("api/terapia/turno")
@CrossOrigin(origins = "*")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public List<Turno> listaTurnos() {
        return turnoService.getAllTurnos();
    }

    @PostMapping
    public Turno create(@RequestBody Turno turno) {
        return turnoService.save(turno);
    }

    @PutMapping("/{id}")
    public Turno update(@PathVariable Long id, @RequestBody Turno turno) {
        turno.setId(id);
        return turnoService.save(turno);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        turnoService.deleteById(id);
    }
}
