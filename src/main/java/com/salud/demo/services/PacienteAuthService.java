package com.salud.demo.services;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Paciente;
import com.salud.demo.services.auth.PacienteCredentialPolicy;

/** Autenticación de pacientes (separada de PacienteService, que solo hace CRUD). */
@Service
public class PacienteAuthService {

    private final PacienteService pacienteService;
    private final PacienteCredentialPolicy credentialPolicy;

    public PacienteAuthService(PacienteService pacienteService, PacienteCredentialPolicy credentialPolicy) {
        this.pacienteService = pacienteService;
        this.credentialPolicy = credentialPolicy;
    }

    /** @return el paciente autenticado, o null si el DNI o la contraseña no coinciden. */
    public Paciente login(String dni, String password) {
        Paciente paciente = pacienteService.getByDNI(dni);
        if (paciente == null) {
            return null;
        }
        return credentialPolicy.coincide(paciente, password) ? paciente : null;
    }
}
