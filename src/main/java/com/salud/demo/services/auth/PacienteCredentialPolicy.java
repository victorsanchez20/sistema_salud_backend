package com.salud.demo.services.auth;

import com.salud.demo.models.Paciente;

/**
 * Estrategia para validar las credenciales de un paciente.
 * Permite cambiar la regla (p. ej. contraseña propia con hash) agregando otra
 * implementación, sin modificar PacienteAuthService (OCP / DIP).
 */
public interface PacienteCredentialPolicy {

    boolean coincide(Paciente paciente, String passwordIngresada);
}
