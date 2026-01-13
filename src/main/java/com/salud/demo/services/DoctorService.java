package com.salud.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.salud.demo.models.Doctor;
import com.salud.demo.repositories.CitaRepository;
import com.salud.demo.repositories.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final CitaRepository citaRepository;

    public DoctorService(DoctorRepository doctorRepository, CitaRepository citaRepository) {
        this.doctorRepository = doctorRepository;
        this.citaRepository = citaRepository;
    }

    public List<Doctor> getAllDoctor() {
        return doctorRepository.findAll();
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }
    
    public Map<String, Object> obtenerDoctorConPacientes(String nombre) {

        Doctor doctor = doctorRepository.findByNombre(nombre)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        List<?> pacientes = citaRepository.findPacientesByDoctorId(doctor.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("doctor", doctor);
        response.put("pacientes", pacientes);

        return response;
    }
}
