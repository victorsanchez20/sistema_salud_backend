package com.salud.demo.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.salud.demo.models.Sesion;
import com.salud.demo.repositories.SesionRepository;

@Service
public class SesionService {

    
    private final SesionRepository sesionRepository;

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }
    public List<Sesion> getAllSesions() {
        return sesionRepository.findAll();
    }
    public Sesion save(Sesion sesion) {
        if (sesionRepository.existsByFecha(sesion.getFecha())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "La hora seleccionada ya está ocupada"
            );
        }
        return sesionRepository.save(sesion);
    }
    public void deleteById(Long id) {
        sesionRepository.deleteById(id);
    } 

    public List<Sesion> getSesionesPorPaciente(Long idPaciente) {
        // traer todas las sesiones donde la cita pertenece al paciente
        return sesionRepository.findByCita_Paciente_Id(idPaciente);
    }

    public List<Sesion> getSesionesPorCita(Long idCita) {
        return sesionRepository.findByCita_Id(idCita);
    }

     public List<Sesion> obtenerSesionesDelDiaMasCercano() {

        List<Sesion> proximas = sesionRepository.findProximaSesion(
            PageRequest.of(0, 1)
        );

        if (proximas.isEmpty()) {
            return List.of();
        }

        LocalDate fechaMasCercana = proximas.get(0)
            .getFecha()
            .toLocalDate();

        return sesionRepository.findSesionesPorFecha(fechaMasCercana);
    }

}
