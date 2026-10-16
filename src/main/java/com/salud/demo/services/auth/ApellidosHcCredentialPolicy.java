package com.salud.demo.services.auth;

import org.springframework.stereotype.Component;

import com.salud.demo.models.Paciente;

/** Regla vigente: contraseña = apellido paterno + código HC + apellido materno. */
@Component
public class ApellidosHcCredentialPolicy implements PacienteCredentialPolicy {

    @Override
    public boolean coincide(Paciente paciente, String passwordIngresada) {
        String esperada = limpio(paciente.getApaterno()) + limpio(paciente.getHc()) + limpio(paciente.getAmaterno());
        return esperada.equals(passwordIngresada);
    }

    private String limpio(String valor) {
        return valor != null ? valor.trim() : "";
    }
}
