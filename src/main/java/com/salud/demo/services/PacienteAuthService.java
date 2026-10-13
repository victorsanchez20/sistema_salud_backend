package com.salud.demo.services;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Paciente;

/** Autenticación de pacientes (separada de PacienteService, que solo hace CRUD). */
@Service
public class PacienteAuthService {

    private final PacienteService pacienteService;

    public PacienteAuthService(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /** @return el paciente autenticado, o null si el DNI o la contraseña no coinciden. */
    public Paciente login(String dni, String password) {
        Paciente paciente = pacienteService.getByDNI(dni);
        if (paciente == null) {
            return null;
        }

        String apaterno = paciente.getApaterno() != null ? paciente.getApaterno().trim() : "";
        String amaterno = paciente.getAmaterno() != null ? paciente.getAmaterno().trim() : "";
        String hc = paciente.getHc() != null ? paciente.getHc().trim() : "";
        String expectedPassword = apaterno + hc + amaterno;

        return expectedPassword.equals(password) ? paciente : null;
    }
}
