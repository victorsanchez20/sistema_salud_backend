package com.salud.demo.services;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salud.demo.dto.LoginRequestDTO;
import com.salud.demo.dto.LoginResponseDTO;
import com.salud.demo.models.Administrador;
import com.salud.demo.repositories.AdministradorRepository;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    public AdministradorService(AdministradorRepository administradorRepository,
                                PasswordEncoder passwordEncoder,
                                Clock clock
    ) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
        this.clock = clock;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Administrador admin = administradorRepository
            .findByUsernameAndActivoTrue(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordValida(admin, request.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (esClaveEnTextoPlano(admin, request.getPassword())) {
            migrarAHash(admin, request.getPassword());
        }

        admin.setUltimoLogin(LocalDateTime.now(clock));
        administradorRepository.save(admin);

        return aRespuesta(admin);
    }

    private boolean passwordValida(Administrador admin, String passwordIngresada) {
        return passwordEncoder.matches(passwordIngresada, admin.getPassword())
            || esClaveEnTextoPlano(admin, passwordIngresada);
    }

    /** Cuentas antiguas guardaban la clave sin cifrar: se aceptan y se migran a BCrypt. */
    private boolean esClaveEnTextoPlano(Administrador admin, String passwordIngresada) {
        return admin.getPassword() != null && admin.getPassword().equals(passwordIngresada);
    }

    private void migrarAHash(Administrador admin, String passwordPlana) {
        admin.setPassword(passwordEncoder.encode(passwordPlana));
    }

    private LoginResponseDTO aRespuesta(Administrador admin) {
        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(admin.getId());
        response.setNombre(admin.getNombre());
        response.setUsername(admin.getUsername());
        response.setEmail(admin.getEmail());
        response.setRol(admin.getRol());
        return response;
    }
}
