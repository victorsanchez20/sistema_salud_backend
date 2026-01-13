package com.salud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salud.demo.models.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

}
