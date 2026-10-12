package com.salud.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.salud.demo.exception.RecursoNoEncontradoException;
import com.salud.demo.models.Doctor;
import com.salud.demo.repositories.CitaRepository;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.services.support.CrudService;

@Service
public class DoctorService extends CrudService<Doctor, Long> {

    private final DoctorRepository doctorRepository;
    private final CitaRepository citaRepository;

    public DoctorService(DoctorRepository doctorRepository, CitaRepository citaRepository) {
        super(doctorRepository);
        this.doctorRepository = doctorRepository;
        this.citaRepository = citaRepository;
    }

    public List<Doctor> getAllDoctor() {
        return findAll();
    }

    public Doctor save(Doctor doctor) {
        return guardar(doctor);
    }

    public void deleteById(Long id) {
        eliminarPorId(id);
    }

    public Map<String, Object> obtenerDoctorConPacientes(String nombre) {

        Doctor doctor = doctorRepository.findByNombre(nombre)
            .orElseThrow(() -> RecursoNoEncontradoException.de("Doctor"));

        List<?> pacientes = citaRepository.findPacientesByDoctorId(doctor.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("doctor", doctor);
        response.put("pacientes", pacientes);

        return response;
    }
}
