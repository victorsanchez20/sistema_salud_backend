package com.salud.demo.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.salud.demo.dto.LoginRequestDTO;
import com.salud.demo.dto.LoginResponseDTO;
import com.salud.demo.models.Administrador;
import com.salud.demo.repositories.AdministradorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministradorService(AdministradorRepository administradorRepository,
                                PasswordEncoder passwordEncoder
    ) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Administrador admin = administradorRepository
            .findByUsernameAndActivoTrue(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String storedPassword = admin.getPassword();
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), storedPassword);

        if (!passwordMatches && storedPassword != null && storedPassword.equals(request.getPassword())) {
            passwordMatches = true;
        }

        if (!passwordMatches) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (storedPassword != null && storedPassword.equals(request.getPassword())) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Actualizar ultimo login
        admin.setUltimoLogin(LocalDateTime.now());
        administradorRepository.save(admin);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(admin.getId());
        response.setNombre(admin.getNombre());
        response.setUsername(admin.getUsername());
        response.setEmail(admin.getEmail());
        response.setRol(admin.getRol());

        return response;
    }
}