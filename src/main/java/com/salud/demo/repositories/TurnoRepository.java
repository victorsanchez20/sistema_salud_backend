package com.salud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salud.demo.models.Turno;


public interface TurnoRepository extends JpaRepository<Turno, Long> {

}
