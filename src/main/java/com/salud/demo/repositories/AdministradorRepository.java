package com.salud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salud.demo.models.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
        Optional<Administrador> findByUsernameAndActivoTrue(String username);

}
