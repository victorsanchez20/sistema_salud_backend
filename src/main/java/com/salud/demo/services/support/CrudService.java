package com.salud.demo.services.support;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Operaciones CRUD básicas compartidas por los servicios de catálogo
 * (turnos, diagnósticos, doctores, citas, disponibilidad).
 * Elimina el código repetido getAll / save / deleteById (DRY).
 */
public abstract class CrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected CrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    protected List<T> findAll() {
        return repository.findAll();
    }

    protected T guardar(T entidad) {
        return repository.save(entidad);
    }

    protected void eliminarPorId(ID id) {
        repository.deleteById(id);
    }
}
