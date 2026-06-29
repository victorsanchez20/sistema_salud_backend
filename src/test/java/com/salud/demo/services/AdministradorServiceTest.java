package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.salud.demo.dto.LoginRequestDTO;
import com.salud.demo.dto.LoginResponseDTO;
import com.salud.demo.models.Administrador;
import com.salud.demo.repositories.AdministradorRepository;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceTest {

    @Mock
    private AdministradorRepository administradorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdministradorService administradorService;

    @Captor
    private ArgumentCaptor<Administrador> administradorCaptor;

    private Administrador admin;
    private LoginRequestDTO loginRequest;

    @BeforeEach
    void setUp() {
        admin = new Administrador(1L, "admin", "12345", "Admin", "admin@test.com", "ADMIN", true, LocalDateTime.now(), null);
        loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("12345");
    }

    @Test
    void loginSuccessfully() {
        when(administradorRepository.findByUsernameAndActivoTrue("admin")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("12345", "12345")).thenReturn(true);

        LoginResponseDTO response = administradorService.login(loginRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Admin", response.getNombre());
        assertEquals("admin", response.getUsername());
        assertEquals("admin@test.com", response.getEmail());
        assertEquals("ADMIN", response.getRol());

        verify(administradorRepository).findByUsernameAndActivoTrue("admin");
        verify(administradorRepository).save(administradorCaptor.capture());
        assertNotNull(administradorCaptor.getValue().getUltimoLogin());
    }

    @Test
    void loginUserNotFound() {
        when(administradorRepository.findByUsernameAndActivoTrue("admin")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.login(loginRequest);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(administradorRepository, never()).save(any());
    }

    @Test
    void loginWrongPassword() {
        when(administradorRepository.findByUsernameAndActivoTrue("admin")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("wrong", "12345")).thenReturn(false);
        loginRequest.setPassword("wrong");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.login(loginRequest);
        });

        assertEquals("Contraseña incorrecta", exception.getMessage());
        verify(administradorRepository, never()).save(any());
    }

    @Test
    void loginHashesPlainTextPasswordOnSuccess() {
        admin.setPassword("12345");
        when(administradorRepository.findByUsernameAndActivoTrue("admin")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("12345", "12345")).thenReturn(true);
        when(passwordEncoder.encode("12345")).thenReturn("$2a$10$hashedPassword");

        administradorService.login(loginRequest);

        verify(administradorRepository).save(administradorCaptor.capture());
        assertEquals("$2a$10$hashedPassword", administradorCaptor.getValue().getPassword());
    }
}
